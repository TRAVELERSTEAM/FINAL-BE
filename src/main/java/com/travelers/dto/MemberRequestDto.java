package com.travelers.dto;

import com.travelers.annotation.Tel;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    private String birth;

    @NotNull
    @Tel
    private String tel;

    private String groupTrip;

    private String area;

    private String theme;

    private String recommend;

    @NotNull
    private Gender gender;

    public Member toMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .tel(tel)
                .birth(birth)
                .groupTrip(groupTrip)
                .area(area)
                .theme(theme)
                .recommend(recommend)
                .gender(gender)
                .build();
    }
}
