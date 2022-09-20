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

    @NotNull
    private String hobby;

    @NotNull
    private String prefer;

    @NotNull
    private Gender gender;

    public Member toMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .tel(tel)
                .birth(birth)
                .hobby(hobby)
                .prefer(prefer)
                .gender(gender)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
