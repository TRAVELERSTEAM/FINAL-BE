package com.travelers.biz.service;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.*;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
    public MemberFindEmailResponseDto getMemberEmailInfo(String username, String birth, Gender gender) {
        return memberRepository.findByUsernameAndBirthAndGender(username, birth, gender)
                .map(MemberFindEmailResponseDto::of)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "검색된 회원 정보가 없습니다."));
    }

    // 해당 유저이름과 생년월일, 성별, 생년월일, 전화번호, 이메일에 해당하는 맴버 리턴
    @Transactional(readOnly = true)
    public Member getMemberInfo (String username, String birth, Gender gender, String tel, String email) {
        return memberRepository.findByUsernameAndBirthAndGenderAndTelAndEmail(username, birth, gender, tel, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "검색된 회원 정보가 없습니다."));
    }

    /******************************************************************************************************************/

    // USER (회원)
    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "현재 내 계정 정보가 존재하지 않습니다"));
    }

    // 회원 탈퇴하기
    @Transactional
    public void deleteMyAccount() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "로그인 유저 정보가 없습니다."));
        Token token = tokenRepository.findById(String.valueOf(SecurityUtil.getCurrentMemberId()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "토큰 정보가 없습니다."));
        memberRepository.delete(member);
        tokenRepository.delete(token);
    }

    /******************************************************************************************************************/

    // ADMIN (관리자)
    // 회원 등급 변경
    @Transactional
    public void updateAuthority(Long id, AuthorityResponseDto authority){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "로그인 유저 정보가 없습니다."));
        member.changeAuthority(authority.toAuthority());
        memberRepository.save(member);
    }

    @Transactional
    public void deleteByMemberId(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "로그인 유저 정보가 없습니다."));
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "토큰 정보가 없습니다."));
        memberRepository.delete(member);
        tokenRepository.delete(token);
    }

    // 해당 이메일이 존재하는지 체크
    public boolean checkEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isEmpty();
    }

    // 회원 중복 체크(이메일)
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원 등급 체크
    public boolean checkMemberAuthority(String email){
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "존재하지 않는 회원입니다."));
        return Authority.isAdmin(findMember.getAuthority());
    }

    // 비밀번호 암호화해서 변경하기
    public void changePassword(Member member, String password, MemberFindPasswordRequestDto memberFindPasswordRequestDto) {
        Member tempMember = memberFindPasswordRequestDto
                .toMember(member, passwordEncoder, password);
        tempMember.setId(member.getId());
        memberRepository.save(tempMember);
    }
}
