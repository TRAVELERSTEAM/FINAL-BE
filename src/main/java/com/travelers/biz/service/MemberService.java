package com.travelers.biz.service;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.AuthorityResponseDto;
import com.travelers.dto.MemberResponseDto;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    // USER
    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    // 회원 탈퇴하기
    @Transactional
    public void deleteMyAccount() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        Token token = tokenRepository.findById(String.valueOf(SecurityUtil.getCurrentMemberId()))
                        .orElseThrow(() -> new RuntimeException("토큰 정보가 없습니다."));
        memberRepository.delete(member);
        tokenRepository.delete(token);
    }

    // ADMIN
    // 회원 등급 변경
    @Transactional
    public void updateAuthority(Long id, AuthorityResponseDto authority){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        member.changeAuthority(authority.toAuthority());
        memberRepository.save(member);
    }

    // 회원 중복 체크(이메일)
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원 등급 체크
    public boolean checkMemberAuthority(String email){
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return Authority.isAdmin(findMember.getAuthority());
    }
}
