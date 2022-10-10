package com.travelers.biz.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnonymousMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anonymous_member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "reservation_code")
    private String reservationCode;

    @Builder
    private AnonymousMember(
            final String username,
            final String tel,
            final String email
    ) {
        this.username = username;
        this.tel = tel;
        this.email = email;
    }
}
