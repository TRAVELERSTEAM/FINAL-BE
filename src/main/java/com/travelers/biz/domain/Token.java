package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token")
public class Token {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    private Token(String id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token accessUpdate(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Token refreshUpdate(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
