package com.travelers.dto;

import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFindEmailResponseDto {

    @NotNull
    private String email;

    public static MemberFindEmailResponseDto of(Member member){
        return new MemberFindEmailResponseDto(member.getEmail());
    }
}
