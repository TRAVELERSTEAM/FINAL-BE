package com.travelers.admin.controller;

import com.travelers.biz.service.AuthService;
import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberLoginRequestDto;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminAuthController {

    private final MemberService memberService;
    private final AuthService authService;

    // 어드민 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        return ResponseEntity.ok(authService.login(memberLoginRequestDto));
    }
}
