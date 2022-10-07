package com.travelers.api.controller;

import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    // 내 정보 변경
    @PutMapping("/user")
    public ResponseEntity<Objects> updateMyInfo(
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @RequestPart(value = "request") MemberRequestDto.ChangeInfo changeInfo
            ) throws IOException {
        memberService.changeMyInfo(changeInfo, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<Objects> DeleteMyAccount() {
        memberService.deleteMyAccount();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
