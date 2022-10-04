package com.travelers.api.controller;

import com.travelers.exception.TravelersException;
import com.travelers.biz.service.EmailService;
import com.travelers.dto.EmailRequestDto;
import com.travelers.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.travelers.exception.ErrorCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    private final long EMAIL_EXPIRE_TIME = 60 * 5L;

    // 회원가입 이메일 인증번호 발송
    @PostMapping("/verify")
    public ResponseEntity<Objects> joinMail(@RequestBody EmailRequestDto emailRequestDto) {
        String key = emailService.joinMail(emailRequestDto.getEmail());
        redisUtil.setDataExpire(key, emailRequestDto.getEmail(), EMAIL_EXPIRE_TIME);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원가입 이메일 인증번호 확인
    @GetMapping("/verify/{email}/{key}")
    public ResponseEntity<Objects> verifyEmailKey(@PathVariable String email, @PathVariable String key){
        if(emailService.verifyKey(email, key)) {
            if(emailService.changeExpireKey(email, key)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else throw new TravelersException(KEY_NOT_FOUND);
        }
        else throw new TravelersException(KEY_NOT_FOUND);
    }
}
