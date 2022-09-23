package com.travelers.dto;

import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.ProductImage;
import com.travelers.biz.domain.ProductStartDate;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-06
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String title;
    private Integer price;
    private String thumbnail;
    private String target;
    private String destination;
    private String theme;
    private Integer priority;
    private String summary;
    private String packaging;
    @Builder.Default
    private List<Integer> startDate = new ArrayList<>();
    @Builder.Default
    private List<String> image = new ArrayList<>();

    public void setProduct(Product product) {
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.thumbnail = product.getThumbnail();
        this.target = product.getTarget();
        this.destination = product.getDestination();
        this.theme = product.getTheme();
        this.priority = product.getPriority();
        this.summary = product.getSummary();
        this.packaging = product.getPackaging();
        for (ProductStartDate date: product.getStartDates()) {
            this.startDate.add(date.getStartDate());
        }
        for (ProductImage image: product.getImages()) {
            this.image.add(image.getImage());
        }
    }
}
