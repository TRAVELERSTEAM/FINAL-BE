package com.travelers.biz.service;

import com.travelers.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    Random ran = new Random();
    StringBuffer buf =new StringBuffer();

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private String authStringNumber;
    private int authNumber;

    public void makeRandomNumber() {
        int num = ran.nextInt(888888) + 111111;
        log.info("이메일 인증번호 : " + num);
        this.authNumber = num;
    }

    public void makeRandomStringNumber() {
        for(int i = 0; i < 10; i++) {
            if(ran.nextBoolean()) buf.append((char)((int)(ran.nextInt(26)) + 65));
            else buf.append((ran.nextInt(10)));
        }
        this.authStringNumber = buf.toString();
    }

    // 회원가입 인증번호 및 메시지 생성
    public String joinMail(String email) {
        makeRandomNumber();
        String setFrom = "lmhtest0237@gmail.com";
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
                "고투게더 홈페이지를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        sendMail(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    // 비밀번호 찾기 인증번호 발송
    public String joinResetPassword(String email) {
        makeRandomStringNumber();
        String setFrom = "lmhtest0237@gmail.com";
        String toMail = email;
        String title = "임시 비밀번호 발급 이메일 입니다.";
        String content =
                "고투게더 홈페이지를 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "임시 비밀번호는 " + authStringNumber + "입니다." +
                        "<br>" +
                        "비밀번호로 로그인 후 꼭 비밀번호를 재설정 해주세요!";
        sendMail(setFrom, toMail, title, content);
        return authStringNumber;
    }


    // 메일 전송
    public void sendMail(String setFrom, String toMail, String title, String content){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);

            helper.setText(content, true);
//            mailSender.send(message);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // 회원가입 인증번호 Redis 검증
    public boolean verifyKey(String email, String key){
        Optional<String> memberEmail = Optional.ofNullable(redisUtil.getData(key));
        if(memberEmail.isEmpty()) return false;
        else return memberEmail.get().equals(email);
    }



}
