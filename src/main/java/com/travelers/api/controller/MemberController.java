package com.travelers.api.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    // 내 정보 확인
    @PostMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyInfo(){
        return ResponseEntity.ok(memberService.getMyInfo());
    }
}
