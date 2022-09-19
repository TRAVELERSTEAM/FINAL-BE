package com.travelers.jwt;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.access}")
    private String SECRET_KEY;
    @Value("${jwt.secret.refresh}")
    private String REFRESH_KEY;

    private final long ACCESS_TOKEN_TIME = 1 * 60 * 1000L;      // 1분
    private final long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주일

    // 의존성 주입이 이루어진 후 키값을 초기화 하기위해 설정
    @PostConstruct
    private void init() {
        log.info("BEFORE : " + SECRET_KEY);
        log.info("BEFORE : " + REFRESH_KEY);

        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());

        log.info("AFTER : " + SECRET_KEY);
        log.info("AFTER : " + REFRESH_KEY);
    }

}
