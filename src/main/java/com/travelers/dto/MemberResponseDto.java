package com.travelers.dto;

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
    private String hobby;
    private String prefer;
    private Gender gender;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUsername(),
                member.getEmail(), member.getBirth(), member.getTel(),
                member.getHobby(), member.getPrefer(), member.getGender());
    }
}
