package com.travelers.jwt;

import com.travelers.dto.TokenResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.access}")
    private String SECRET_KEY;
    @Value("${jwt.secret.refresh}")
    private String REFRESH_KEY;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private final long ACCESS_TOKEN_TIME = 1 * 60 * 1000L;      // 1분
    private final long REFRESH_TOKEN_TIME = 1 * 60 * 3 * 1000L;   // 3분

    private Key key;

    // 의존성 주입이 이루어진 후 키값을 초기화 하기위해 설정
    @PostConstruct
    private void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public TokenResponseDto generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "1"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenResponseDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Claims getAccessClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getRefreshClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(REFRESH_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Claims accessClaims = getAccessClaimsToken(accessToken);
            log.info("Access expireTime: " + accessClaims.getExpiration());
            log.info("Access memberId: " + accessClaims.get("sub"));
            log.info("Access auth: " + accessClaims.get("auth"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Token Expired memberId : " + exception.getClaims().get("sub"));
            return false;
        } catch (JwtException exception) {
            log.info("JwtException : " + accessToken);
            log.info("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.info("Token is null");
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Claims accessClaims = getRefreshClaimsToken(refreshToken);
            log.info("Access expireTime: " + accessClaims.getExpiration());
            log.info("Access memberId: " + accessClaims.get("sub"));
            log.info("Access auth: " + accessClaims.get("auth"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Token Expired memberId : " + exception.getClaims().get("sub"));
            return false;
        } catch (JwtException exception) {
            log.info("JwtException : " + refreshToken);
            log.info("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.info("Token is null");
            return false;
        }
    }

    private Claims parseClaims(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        log.info(claims.toString());
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
