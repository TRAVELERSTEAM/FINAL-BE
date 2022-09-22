package com.travelers.dto;

import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private String tel;

    private String groupTrip;

    private String area;

    private String theme;

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
                .gender(gender)
                .build();
    }
}
