package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author kei
 * @since 2022-09-23
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hashtag;
    private String title;
    private String image;
    private Long productId;

    @Builder
    public Banner(Long id, String hashtag, String title, String image, Long productId) {
        this.id = id;
        this.hashtag = hashtag;
        this.title = title;
        this.image = image;
        this.productId = productId;
    }
}
