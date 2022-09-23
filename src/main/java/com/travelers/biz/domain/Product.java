package com.travelers.biz.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-06
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer price;
    private String thumbnail;
    private String target;
    private String destination;
    private String theme;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductStartDate> startDates = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

    @Builder
    public Product(Long id, String title, Integer price, String thumbnail, String target, String destination, String theme, List<ProductStartDate> startDates, List<ProductImage> images) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.target = target;
        this.destination = destination;
        this.theme = theme;
        this.startDates = startDates;
        this.images = images;
    }
}
