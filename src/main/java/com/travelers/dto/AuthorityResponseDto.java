package com.travelers.dto;

import com.travelers.biz.domain.Authority;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityResponseDto {

    private String authority;

    public Authority toAuthority(){
        return authority.equals(Authority.ROLE_ADMIN.toString()) ? Authority.ROLE_ADMIN : Authority.ROLE_USER;
    }
}
