package com.travelers.dto;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private String username;
    private String email;
    private String birth;
    private String tel;
    private String groupTrip;
    private String area;
    private String theme;
    private Gender gender;
    private Authority authority;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUsername(),
                member.getEmail(), member.getBirth(), member.getTel(),
                member.getGroupTrip(), member.getArea(), member.getTheme(),
                member.getGender(), member.getAuthority());
    }
}
