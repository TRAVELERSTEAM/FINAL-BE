package com.travelers.api.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 내 정보 확인
    @GetMapping("/user/me")
    public ResponseEntity<MemberResponseDto> getMyInfo(){
        return ResponseEntity.ok(memberService.getMyInfo());
    }

    // 비밀번호 변경
    @PutMapping("/user/password")
    public ResponseEntity<Objects> changeMyPassword(@RequestBody MemberRequestDto.MemberChangePasswordRequestDto memberChangePasswordRequestDto) {
        memberService.changeMyPassword(memberChangePasswordRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<Objects> DeleteMyAccount() {
        memberService.deleteMyAccount();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
