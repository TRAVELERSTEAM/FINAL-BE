package com.travelers.dto;

import com.travelers.annotation.Tel;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFindPasswordRequestDto {

    @NotNull
    private String username;

    @NotNull
    private Gender gender;

    @NotNull
    private String birth;

    @NotNull
    @Tel
    private String tel;

    @NotNull
    private String email;

    public Member toMember(Member member, PasswordEncoder passwordEncoder, String password) {
        return Member.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .password(passwordEncoder.encode(password))
                .tel(member.getTel())
                .birth(member.getBirth())
                .groupTrip(member.getGroupTrip())
                .area(member.getArea())
                .theme(member.getTheme())
                .recommend(member.getRecommend())
                .recommendCode(member.getRecommendCode())
                .gender(member.getGender())
                .build();
    }
}
