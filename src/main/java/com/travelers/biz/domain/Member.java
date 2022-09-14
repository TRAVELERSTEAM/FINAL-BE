package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "birth")
    private String birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority;

    @Builder
    private Member(String username, String nickname, String password, String email, String tel, String birth, Gender gender){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.gender = gender;
        this.authority = Authority.ROLE_USER;
    }
}
