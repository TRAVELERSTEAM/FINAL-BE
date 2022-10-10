package com.travelers.dto;

import com.travelers.annotation.Tel;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class ReservationRequest {

    @Getter
    public static class Anonymous {
        @NotNull
        private String name;

        @Email
        private String email;

        @Tel
        private String tel;

        @Valid
        private HeadCount headCount;

        public Member toAnonymousMember() {
            return Member.builder()
                    .username(name)
                    .email(email)
                    .tel(tel)
                    .build();
        }
    }
}
