package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "birth")
    private String birth;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "prefer")
    private String prefer;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority;

    @Builder
    private Member(String username, String password, String email, String tel, String birth, String hobby, String prefer, Gender gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.birth = birth;
        this.hobby = hobby;
        this.prefer = prefer;
        this.gender = gender;
        this.authority = Authority.ROLE_USER;
    }
}
