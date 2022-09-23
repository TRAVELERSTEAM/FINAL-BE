package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author kei
 * @since 2022-09-17
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductStartDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    @ToString.Exclude
    private Product product;

    @Builder
    public ProductStartDate(Long id, Integer startDate, Product product) {
        this.id = id;
        this.startDate = startDate;
        this.product = product;
    }
}