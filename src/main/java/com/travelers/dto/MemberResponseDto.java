package com.travelers.dto;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

    private String username;
    private String email;
    private String birth;
    private String tel;
    private List<String> groupTrip;
    private List<String> area;
    private List<String> theme;
    private String recommendCode;
    private Gender gender;
    private Authority authority;
    private String profile;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUsername(),
                member.getEmail(),
                member.getBirth(),
                member.getTel(),
                Arrays.asList(member.getGroupTrip().split(",")),
                Arrays.asList(member.getArea().split(",")),
                Arrays.asList(member.getTheme().split(",")),
                member.getRecommendCode(),
                member.getGender(),
                member.getAuthority(),
                member.getImages().get(0).getUrl()
        );
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FindEmail {

        @NotNull
        private String email;

        public static FindEmail of(Member member){
            return new FindEmail(member.getEmail());
        }
    }
}
