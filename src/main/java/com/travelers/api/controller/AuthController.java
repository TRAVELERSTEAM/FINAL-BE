package com.travelers.api.controller;

import com.travelers.biz.domain.Member;
import com.travelers.biz.service.AuthService;
import com.travelers.biz.service.EmailService;
import com.travelers.biz.service.MemberService;
import com.travelers.dto.MemberRequestDto;
import com.travelers.dto.MemberResponseDto;
import com.travelers.dto.TokenRequestDto;
import com.travelers.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    // 회원가입
    @PostMapping(path = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MemberResponseDto> register(
            @RequestPart(value="file", required = false) List<MultipartFile> files,
            @RequestPart(value="request") MemberRequestDto request) throws IOException {
        return new ResponseEntity<>(authService.register(request, files), HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody MemberRequestDto.Login login) {
        return ResponseEntity.ok(authService.login(login));
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    // 아이디 찾기
    @PostMapping("/find_email")
    public ResponseEntity<MemberResponseDto.FindEmail> findEmail(@RequestBody MemberRequestDto.FindEmail findEmail){
        return new ResponseEntity<>(memberService.getMemberEmailInfo(findEmail.getUsername(), findEmail.getBirth(), findEmail.getGender()), HttpStatus.OK);
    }

    // 비밀번호 찾기 이메일 인증 후 임시비밀번호 발송
    @PostMapping("/find_password")
    public ResponseEntity<Objects> findPassword(@RequestBody MemberRequestDto.FindPassword findPassword) {
        authService.findPassword(findPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
