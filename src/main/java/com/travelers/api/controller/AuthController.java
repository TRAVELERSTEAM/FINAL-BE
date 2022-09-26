package com.travelers.api.controller;

import com.travelers.biz.service.AuthService;
import com.travelers.biz.service.EmailService;
import com.travelers.biz.service.MemberService;
import com.travelers.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final EmailService emailService;
    private final AuthService authService;
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Objects> register(@RequestBody MemberRequestDto memberRequestDto) {
        authService.register(memberRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        return ResponseEntity.ok(authService.login(memberLoginRequestDto));
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    // 아이디 찾기
    @PostMapping("/find_email")
    public ResponseEntity<MemberFindEmailResponseDto> findEmail(@RequestBody MemberFindEmailRequestDto memberFindEmailRequestDto){
        return new ResponseEntity<>(memberService.getMemberInfo(memberFindEmailRequestDto.getUsername(), memberFindEmailRequestDto.getBirth(), memberFindEmailRequestDto.getGender()), HttpStatus.OK);
    }


    // 비밀번호 찾기 이메일 인증번호 발송
//    @PostMapping("/reset_password")
//    public ResponseEntity<Objects> resetPassword(@RequestBody MemberResetPasswordRequestDto memberResetPasswordRequestDto) {
//        if(!memberService.checkEmail(memberResetPasswordRequestDto.getEmail())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        emailService.joinResetPassword(memberResetPasswordRequestDto.getEmail());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
