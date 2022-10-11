package com.travelers.biz.service;

import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.image.MemberImage;
import com.travelers.biz.repository.ImageRepository;
import com.travelers.biz.service.handler.FileUploader;
import com.travelers.biz.service.handler.S3Uploader;
import com.travelers.exception.TravelersException;
import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.AuthorityResponseDto;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.MemberResponseDto;
import com.travelers.util.FileUtils;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${spring.file.directory}")
    private String location;

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final S3Uploader s3Uploader;
    private final EmailService emailService;
    private final ImageRepository imageRepository;
    private final FileUploader fileUploader;

    // 비회원
    // 해당 유저이름과 성별, 생년월일에 해당하는 이메일 리턴
    @Transactional(readOnly = true)
    public MemberResponseDto.FindEmail getMemberEmailInfo(String username, String birth, Gender gender) {
        return memberRepository.findByUsernameAndBirthAndGender(username, birth, gender)
                .map(MemberResponseDto.FindEmail::of)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
    }

    // 해당 유저이름과 생년월일, 성별, 생년월일, 전화번호, 이메일에 해당하는 맴버 리턴
    @Transactional(readOnly = true)
    public Member getMemberInfo (String username, String birth, Gender gender, String tel, String email) {
        return memberRepository.findByUsernameAndBirthAndGenderAndTelAndEmail(username, birth, gender, tel, email)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
    }

    /******************************************************************************************************************/

    // USER (회원)
    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new TravelersException(UNAUTHORIZED_MEMBER));
    }

    // 회원 정보 수정하기
    @Transactional
    public void changeMyInfo(MemberRequestDto.ChangeInfo changeInfo, List<MultipartFile> files) throws IOException {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new TravelersException(ACCESS_TOKEN_NOT_FOUND));

        if(!emailService.verifyKey(changeInfo.getEmail() ,changeInfo.getKey())) {
            throw new TravelersException(KEY_NOT_FOUND);
        }

        // 비밀번호 입력칸이 비어있지 않고
        if(!changeInfo.getCurrentPassword().isEmpty()) {
            // 바꿀 비밀번호와 바꿀 확인비밀번호가 다르면
            if(!checkPasswordIsSame(changeInfo.getChangePassword(), changeInfo.getConfirmChangePassword())) {
                throw new TravelersException(PASSWORD_NOT_MATCHING);
            }

            // 입력받은 비밀번호가 현재 비밀번호와 일치하지 않으면
            if(!passwordEncoder.matches(changeInfo.getCurrentPassword(), member.getPassword())) {
                throw new TravelersException(CURRENT_PASSWORD_NOT_MATCHING);
            }
            member.changePassword(changeInfo.getChangePassword(), passwordEncoder);
        }

        Member myMember = Member.builder()
                .username(changeInfo.getUsername())
                .gender(changeInfo.getGender())
                .birth(changeInfo.getBirth())
                .tel(changeInfo.getTel())
                .email(changeInfo.getEmail())
                .password(member.getPassword())
                .groupTrip(String.join(",", changeInfo.getGroupTrip()))
                .area(String.join(",", changeInfo.getArea()))
                .theme(String.join(",", changeInfo.getTheme()))
                .recommend(member.getRecommend())
                .recommendCode(member.getRecommendCode())
                .build();

        myMember.setId(member.getId());
        myMember.setCreatedAt(member.getCreatedAt());
        myMember.changeAuthority(member.getAuthority());

        if(files != null && !files.isEmpty()) {
            String storedLocation = FileUtils.getStoredLocation(files.get(0).getOriginalFilename(), location);
            File file = new File(storedLocation);
            FileCopyUtils.copy(files.get(0).getBytes(), file);
            String url = s3Uploader.upload(file, files.get(0).getOriginalFilename());
            update(myMember.getId(), url);
        }

        emailService.deleteKey(changeInfo.getKey());
        memberRepository.save(myMember);
    }

    // 회원 탈퇴하기
    @Transactional
    public void deleteMyAccount() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new TravelersException(ACCESS_TOKEN_NOT_FOUND));
        Token token = tokenRepository.findById(String.valueOf(SecurityUtil.getCurrentMemberId()))
                        .orElseThrow(() -> new TravelersException(TOKEN_NOT_FOUND));
        memberRepository.delete(member);
        tokenRepository.delete(token);
    }

    /******************************************************************************************************************/

    // ADMIN (관리자)
    // 회원 전체 목록
    @Transactional(readOnly = true)
    public List<MemberResponseDto> showAllMember(){
        return memberRepository.findAll()
                .stream().map(MemberResponseDto::of).collect(Collectors.toList());
    }

    // 회원 등급 변경
    @Transactional
    public void updateAuthority(Long id, AuthorityResponseDto authority){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
        member.changeAuthority(authority.toAuthority());
        memberRepository.save(member);
    }

    // 회원 삭제
    @Transactional
    public void deleteByMemberId(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new TravelersException(TOKEN_NOT_FOUND));
        memberRepository.delete(member);
        tokenRepository.delete(token);
    }


    /******************************************************************************************************************/

    // 회원 등급 체크
    public boolean checkMemberAuthority(String email){
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
        return Authority.isAdmin(findMember.getAuthority());
    }

    // 회원 비밀번호 체크(같은지 안같은지)
    public boolean checkPasswordIsSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    // 비밀번호 암호화해서 변경하기
    public void changePassword(Member member, String password) {
        member.changePassword(password, passwordEncoder);
        memberRepository.save(member);
    }

    // 프로필 이미지 업데이트
    public void update(final Long memberId, String url) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
        deleteImage(memberId);
        addImage(member, url);
    }

    // 프로필 이미지 추가
    private void addImage(final Member member, String url) {
        new MemberImage(url, member);
    }

    // 프로필 이미지 삭제
    private void deleteImage(final Long memberId) {
        final Image image = imageRepository.findByMemberId(memberId)
                .orElseThrow(() -> new TravelersException(IMAGE_NOT_FOUND));

        final String key = image.getKey();
        fileUploader.delete(List.of(key));
        imageRepository.delete(image);
    }
}
