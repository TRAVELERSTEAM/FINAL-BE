package com.travelers.biz.domain;

import com.travelers.biz.domain.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id;
    @Column(name = "hashtag")
    private String hashtag;
    @Column(name = "title")
    private String title;
    @Column(name = "subImage")
    private String subImage;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Banner(Long id, String hashtag, String title, String subImage, Product product) {
        this.id = id;
        this.hashtag = hashtag;
        this.title = title;
        this.subImage = subImage;
        this.product = product;
    }
}
