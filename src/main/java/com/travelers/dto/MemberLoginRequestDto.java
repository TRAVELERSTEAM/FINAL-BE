package com.travelers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

}
