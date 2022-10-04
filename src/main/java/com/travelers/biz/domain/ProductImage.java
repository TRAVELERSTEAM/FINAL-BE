package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author kei
 * @since 2022-09-17
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    @ToString.Exclude
    private Product product;

    @Builder
    public ProductImage(Long id, String image, Product product) {
        this.id = id;
        this.image = image;
        this.product = product;
    }
}
