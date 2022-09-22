package com.travelers.jwt;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.access}")
    private String SECRET_KEY;
    @Value("${jwt.secret.refresh}")
    private String REFRESH_KEY;

    Logger logger = LoggerFactory.getLogger(FunctionalWithJWTThrowable.class);

    private final long ACCESS_TOKEN_TIME = 1 * 60 * 1000L;      // 1분
    private final long REFRESH_TOKEN_TIME = 1 * 60 * 3 * 1000L;   // 3분

    // 의존성 주입이 이루어진 후 키값을 초기화 하기위해 설정
    @PostConstruct
    private void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
    }

    // JWT 토큰 생성
    public String createAccessToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }

    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, REFRESH_KEY)
                .compact();
    }

    // Request의 Header에서 token 값을 가져오기
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("accessToken");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("refreshToken");
    }

    public Claims getClaimsFormToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(REFRESH_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidAccessToken(String token) {
        return checkClaims(token);
    }
    public boolean isValidRefreshToken(String token) {
        return checkClaims(token);
    }
    public boolean isOnlyExpiredToken(String token) {
        return checkClaims(token);
    }

    public boolean checkClaims(String token) {
        try {
            Claims accessClaims = getClaimsToken(token);
            log.info("Access expireTime: " + accessClaims.getExpiration());
            log.info("Access email: " + accessClaims.get("email"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Token Expired email : " + exception.getClaims().get("email"));
            return false;
        } catch (JwtException exception) {
            log.info("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.info("Token is null");
            return false;
        }
    }

}
