package com.travelers.biz.service;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.MemberLoginRequestDto;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.TokenResponseDto;
import com.travelers.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public void register(MemberRequestDto memberRequestDto){

        if(checkEmailDuplicate(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        if(!checkPasswordIsSame(memberRequestDto.getPassword(), memberRequestDto.getConfirmPassword())){
            throw new RuntimeException("비밀번호 값이 다릅니다.");
        }

        // 패스워드 인코딩 설정 후 회원가입 처리.
        Member member = memberRequestDto.toMember(passwordEncoder);
        memberRepository.save(member);

        // 해당 맴버의 토큰 발급 후 DB에 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        Token token = Token.builder()
                .member(member)
                .refreshToken(refreshToken)
                .build();
        tokenRepository.save(token);
    }

    // 로그인
    @Transactional
    public TokenResponseDto login(MemberLoginRequestDto memberLoginRequestDto){
        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Token token = tokenRepository.findByMemberEmail(memberLoginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));
        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = "";
        String refreshToken = token.getRefreshToken();

        // refresh 토큰이 유효할 경우 AccessToken 발급
        if(jwtTokenProvider.isValidRefreshToken(refreshToken)) {
            accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
            token.accessUpdate(accessToken);
            return TokenResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        else {
            // 유효하지 않으면 둘다 새로 발급 후 refreshToken DB에 업데이트
            accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
            refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());
            token.accessUpdate(accessToken);
            token.refreshUpdate(refreshToken);
        }

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 회원 중복 체크(이메일)
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원 비밀번호 체크(같은지 안같은지)
    public boolean checkPasswordIsSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    // 회원 등급 체크
    public boolean checkMemberAuthority(String email){
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return Authority.isAdmin(findMember.getAuthority());
    }
}
