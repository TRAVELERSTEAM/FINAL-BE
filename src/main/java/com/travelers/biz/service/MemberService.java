package com.travelers.biz.service;

import com.travelers.Exception.TravelersException;
import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.AuthorityResponseDto;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.MemberResponseDto;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.Exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

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
    public void changeMyPassword(MemberRequestDto.ChangePassword changePassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new TravelersException(ACCESS_TOKEN_NOT_FOUND));

        // 바꿀 비밀번호와 바꿀 확인비밀번호가 다르면
        if(!checkPasswordIsSame(changePassword.getChangePassword(), changePassword.getConfirmChangePassword())) {
            throw new TravelersException(PASSWORD_NOT_MATCHING);
        }

        // 입력받은 비밀번호가 현재 비밀번호와 일치하지 않으면
        if(!changePassword.getCurrentPassword().isEmpty() &&
                !passwordEncoder.matches(changePassword.getCurrentPassword(), member.getPassword())) {
            throw new TravelersException(CURRENT_PASSWORD_NOT_MATCHING);
        }

        member.changePassword(changePassword.getChangePassword(), passwordEncoder);
        memberRepository.save(member);
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
}
