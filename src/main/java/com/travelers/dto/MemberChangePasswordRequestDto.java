package com.travelers.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberChangePasswordRequestDto {

    @NotNull
    private String currentPassword;

    @NotNull
    private String changePassword;

    @NotNull
    private String confirmChangePassword;
}
