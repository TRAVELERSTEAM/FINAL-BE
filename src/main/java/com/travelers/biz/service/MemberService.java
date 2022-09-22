package com.travelers.biz.service;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.MemberLoginRequestDto;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.TokenRequestDto;
import com.travelers.dto.TokenResponseDto;
import com.travelers.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
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

    // 내 정보 확인
    public Member getMyInfo(String accessToken, String refreshToken) {

        // 토큰 정보값이 없으면 리턴시킬 빈 맴버객체 생성
        Member emptyMember = Member.builder().build();

        // 만약 액세스토큰이 유효하면 해당 이메일의 주인에 해당하는 맴버정보 리턴
        if(jwtTokenProvider.isValidAccessToken(accessToken)){
            Claims accessClaims = jwtTokenProvider.getClaimsToken(accessToken);
            String email = String.valueOf(accessClaims.get("email"));
            log.info("access : " + email);
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
            return member;
        } // 엑세스 토큰이 유효하지 않으면 refreshToken 유효성검사후 accessToken 재발급후 맴버 리턴
        else {
            if(jwtTokenProvider.isValidRefreshToken(refreshToken)){
                Claims accessClaims = jwtTokenProvider.getClaimsToken(refreshToken);
                String email = String.valueOf(accessClaims.get("email"));
                log.info("refresh : " + email);
                Member member = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
                Token token = tokenRepository.findByMemberEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));
                accessToken = jwtTokenProvider.createAccessToken(email);
                token.accessUpdate(accessToken);
                return member;
            }
            else{
                return emptyMember;
            }
        }
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
