package com.travelers.dto;

import com.travelers.biz.domain.product.Product;
import com.travelers.biz.domain.product.embeddable.Price;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponseDto {

    private Long id;
    private String title;
    private String target;
    private String area;
    private String theme;
    private List<String> summary;
    private int period;
    private String mainImage;
    private List<String> subImages;
    private Price price;

    public static ProductResponseDto of(Product product) {
        return new ProductResponseDto(product.getId(),
                product.getTitle(),
                product.getTarget(),
                product.getArea(),
                product.getTheme(),
                List.of(product.getSummary().split(",")),
                product.getPeriod(),
                product.getImages().get(0).getUrl(),
                getSubImages(product),
                product.getPrice()
                );
    }

    private static List<String> getSubImages(Product product) {
        List<String> tempSubImages = new ArrayList<>();
        for (int i = 1; i < product.getImages().size(); i++) {
            tempSubImages.add(product.getImages().get(i).getUrl());
        }
        return tempSubImages;
    }
}
