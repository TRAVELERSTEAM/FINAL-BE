package com.travelers.dto;

import com.travelers.biz.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponseDto {

    private Long id;
    private String title;
    private String target;
    private String destination;
    private String theme;
    private List<String> summary;
    private String packaging;
    private int period;
    private String mainImage;
    private List<String> subImages;

    public static ProductResponseDto of(Product product) {
        return new ProductResponseDto(product.getId(),
                product.getTitle(),
                product.getTarget(),
                product.getDestination(),
                product.getTheme(),
                List.of(product.getSummary().split(",")),
                product.getPackaging(),
                product.getPeriod(),
                product.getImages().getFirst().getUrl(),
                getSubImages(product)
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
