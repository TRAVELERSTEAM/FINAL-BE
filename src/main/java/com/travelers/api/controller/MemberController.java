package com.travelers.api.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 내 정보 확인
    @GetMapping("/user/me")
    public ResponseEntity<MemberResponseDto> getMyInfo(){
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public ResponseEntity DeleteMyAccount() {
        memberService.deleteMyAccount();
        return new ResponseEntity(HttpStatus.OK);
    }

}
