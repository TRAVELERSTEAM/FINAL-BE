package com.travelers.biz.service;


import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Token;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.TokenRepository;
import com.travelers.dto.*;
import com.travelers.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Transactional
    public MemberResponseDto register(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이미 가입되어 있는 유저입니다");
        }

        if(!checkPasswordIsSame(memberRequestDto.getPassword(), memberRequestDto.getConfirmPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        if(!emailService.verifyKey(memberRequestDto.getEmail() ,memberRequestDto.getKey())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "회원가입 기간이 초과하였습니다.");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        emailService.deleteKey(memberRequestDto.getKey());
        return MemberResponseDto.of(memberRepository.save(member));
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        Token token = tokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!token.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenResponseDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        Token newToken = token.refreshUpdate(tokenDto.getRefreshToken());
        tokenRepository.save(newToken);

        // 토큰 발급
        return tokenDto;
    }

    // 회원 비밀번호 체크(같은지 안같은지)
    public boolean checkPasswordIsSame(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}