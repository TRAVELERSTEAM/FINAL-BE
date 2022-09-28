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
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer price;
    private String thumbnail;
    private String target;
    private String destination;
    private String theme;
    private Integer priority;
    private String summary;
    private String packaging;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductStartDate> startDates = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductImage> images = new ArrayList<>();

    @Builder
    public Product(Long id, String title, Integer price, String thumbnail, String target, String destination, String theme, Integer priority, String summary, String packaging, List<ProductStartDate> startDates, List<ProductImage> images) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.target = target;
        this.destination = destination;
        this.theme = theme;
        this.priority = priority;
        this.summary = summary;
        this.packaging = packaging;
        this.startDates = startDates;
        this.images = images;
    }
}
