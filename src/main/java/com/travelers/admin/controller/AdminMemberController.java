package com.travelers.admin.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.AuthorityResponseDto;
import com.travelers.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMemberController {

    private final MemberService memberService;

    // 전체 회원 목록 출력
    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> allMember(){
        return ResponseEntity.ok(memberService.showAllMember());
    }

    // 회원 등급 변경
    @PutMapping("/member/{id}")
    public ResponseEntity<Objects> updateAuthority(@PathVariable Long id, @RequestBody AuthorityResponseDto authority){
        memberService.updateAuthority(id, authority);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 특정 회원 탈퇴
    @DeleteMapping("/member/{id}")
    public ResponseEntity<Objects> deleteMember(@PathVariable Long id) {
        memberService.deleteByMemberId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
