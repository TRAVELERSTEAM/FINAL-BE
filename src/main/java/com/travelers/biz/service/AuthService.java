package com.travelers.biz.service;


import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.image.MemberImage;
import com.travelers.biz.repository.ImageRepository;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.biz.service.handler.FileUploader;
import com.travelers.biz.service.handler.S3Uploader;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.MemberResponseDto;
import com.travelers.dto.TokenRequestDto;
import com.travelers.dto.TokenResponseDto;
import com.travelers.exception.TravelersException;
import com.travelers.jwt.JwtTokenProvider;
import com.travelers.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.travelers.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${spring.file.directory}")
    private String location;

    @Value("${profileImage}")
    private String url;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final FileUploader fileUploader;
    private final MemberService memberService;
    private final EmailService emailService;
    private final S3Uploader s3Uploader;

    @Transactional
    public MemberResponseDto register(MemberRequestDto memberRequestDto, List<MultipartFile> files) throws IOException {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new TravelersException(DUPLICATE_ACCOUNT);
        }

        if(!checkPasswordIsSame(memberRequestDto.getPassword(), memberRequestDto.getConfirmPassword())){
            throw new TravelersException(PASSWORD_NOT_MATCHING);
        }

        if(!emailService.verifyKey(memberRequestDto.getEmail() ,memberRequestDto.getKey())) {
            throw new TravelersException(KEY_NOT_FOUND);
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        emailService.deleteKey(memberRequestDto.getKey());

        memberRepository.save(member);

        Member myMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
        if(files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
            String storedLocation = FileUtils.getStoredLocation(files.get(0).getOriginalFilename(), location);
            File file = new File(storedLocation);
            FileCopyUtils.copy(files.get(0).getBytes(), file);
            String s3url = s3Uploader.upload(file, files.get(0).getOriginalFilename());
            addImage(myMember, s3url);
        }
        else {
            addImage(myMember, url);
        }

        return MemberResponseDto.of(memberRepository.save(myMember));
    }

    @Transactional
    public TokenResponseDto login(MemberRequestDto.Login login) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponseDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 4. Token 저장
        Token token = Token.builder()
                .id(authentication.getName())
                .accessToken(tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        tokenRepository.save(token);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateRefreshToken(tokenRequestDto.getRefreshToken())) {
            throw new TravelersException(INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        Token token = tokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new TravelersException(REFRESH_TOKEN_NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!token.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new TravelersException(MISMATCH_REFRESH_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenResponseDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        Token newToken = token.refreshUpdate(tokenDto.getRefreshToken());
        tokenRepository.save(newToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public void findPassword(MemberRequestDto.FindPassword findPassword) {
        Member member = memberRepository.findByUsernameAndBirthAndGenderAndTelAndEmail(
                findPassword.getUsername(),
                findPassword.getBirth(),
                findPassword.getGender(),
                findPassword.getTel(),
                findPassword.getEmail()
        ).orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));

        if(!emailService.verifyKey(findPassword.getEmail(), findPassword.getKey())){
            throw new TravelersException(KEY_NOT_FOUND);
        }
        String tempPassword = emailService.joinResetPassword(findPassword.getEmail());
        memberService.changePassword(member, tempPassword);
    }

    // 회원 비밀번호 체크(같은지 안같은지)
    public boolean checkPasswordIsSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    // 프로필 이미지 추가
    private void addImage(final Member member, String url) {
        new MemberImage(url, member);
    }

    // 프로필 이미지 업데이트
    public void update(final Long memberId, String url) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new TravelersException(MEMBER_NOT_FOUND));
        deleteImage(memberId);
        addImage(member, url);
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