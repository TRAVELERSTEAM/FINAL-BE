package com.travelers.biz.domain;

import lombok.Getter;

@Getter
public enum Authority {
    ROLE_ADMIN, ROLE_USER;

    public static boolean isAdmin(Authority target) {
        return Authority.ROLE_ADMIN == target;
    }
}
