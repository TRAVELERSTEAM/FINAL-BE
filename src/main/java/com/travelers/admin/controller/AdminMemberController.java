package com.travelers.admin.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberLoginRequestDto;
import com.travelers.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    // 어드민 로그인
    @PostMapping("/admin/login")
    public ResponseEntity login(@RequestBody MemberLoginRequestDto memberLoginRequestDto){
        if(memberService.checkEmailDuplicate(memberLoginRequestDto.getEmail())) {
            if(memberService.checkMemberAuthority(memberLoginRequestDto.getEmail())){
                return ResponseEntity.ok(memberService.login(memberLoginRequestDto));
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
