package com.travelers.dto;

import com.travelers.annotation.Tel;
import com.travelers.biz.domain.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResetPasswordRequestDto {

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
}
