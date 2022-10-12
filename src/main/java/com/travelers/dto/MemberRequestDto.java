package com.travelers.dto;

import com.travelers.annotation.Tel;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    private String key;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    private String birth;

    @NotNull
    @Tel
    private String tel;

    private List<String> groupTrip = new ArrayList<>();

    private List<String> area = new ArrayList<>();

    private List<String> theme = new ArrayList<>();

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
                .groupTrip(String.join(",", groupTrip))
                .area(String.join(",", area))
                .theme(String.join(",", theme))
                .recommend(recommend)
                .gender(gender)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChangeInfo {

        @NotNull
        private String username;

        @NotNull
        private Gender gender;

        @Email
        @NotNull
        private String email;

        @NotNull
        private String key;

        @NotNull
        private String birth;

        @NotNull
        @Tel
        private String tel;

        private String currentPassword;

        private String changePassword;

        private String confirmChangePassword;

        private List<String> groupTrip = new ArrayList<>();

        private List<String> area = new ArrayList<>();

        private List<String> theme = new ArrayList<>();

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FindEmail {

        @NotNull
        private String username;

        @NotNull
        private Gender gender;

        @NotNull
        private String birth;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FindPassword {

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

        @NotNull
        private String key;

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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Login {

        @Email
        @NotNull
        private String email;

        @NotNull
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }
}
