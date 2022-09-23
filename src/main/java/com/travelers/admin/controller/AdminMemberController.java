package com.travelers.admin.controller;

import com.travelers.biz.service.AuthService;
import com.travelers.biz.service.MemberService;
import com.travelers.dto.AuthorityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMemberController {

    private final MemberService memberService;
    private final AuthService authService;

    // 회원 등급 변경
    @PutMapping("/member/{id}")
    public ResponseEntity updateAuthority(@PathVariable Long id, @RequestBody AuthorityResponseDto authority){
        memberService.updateAuthority(id, authority);
        return new ResponseEntity(HttpStatus.OK);
    }
}
