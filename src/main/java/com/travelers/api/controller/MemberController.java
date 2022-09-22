package com.travelers.api.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberLoginRequestDto;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MemberRequestDto memberRequestDto){
        if(memberService.checkEmailDuplicate(memberRequestDto.getEmail())){
            return ResponseEntity.badRequest().build();
        }
        else{
            memberService.register(memberRequestDto);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        if(memberService.checkEmailDuplicate(memberLoginRequestDto.getEmail())) {
            return ResponseEntity.ok(memberService.login(memberLoginRequestDto));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    // 내 정보 확인
    @PostMapping("/user/me")
    public ResponseEntity myInfo(HttpServletRequest req){
        String accessToken = req.getHeader("accessToken");
        String refreshToken = req.getHeader("refreshToken");

        return ResponseEntity.ok(memberService.getMemberInfo(accessToken, refreshToken));
    }

}
