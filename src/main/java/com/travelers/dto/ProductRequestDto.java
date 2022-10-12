package com.travelers.dto;

import com.travelers.biz.domain.image.ProductImage;
import com.travelers.biz.domain.product.Product;
import com.travelers.biz.domain.product.embeddable.Price;
import lombok.*;

import java.time.Period;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequestDto {

    private Long id;
    private String title;
    private String target;
    private String area;
    private String theme;
    private List<String> startDate;
    private List<String> summary;
    private int period;
    private String mainImage;
    private List<String> subImages;
    private Price price;

    @Builder
    public ProductRequestDto(Long id, String title, String target, String area, String theme, List<String> startDate, List<String> summary, int period, String mainImage, List<String> subImages, Price price) {
        this.id = id;
        this.title = title;
        this.target = target;
        this.area = area;
        this.theme = theme;
        this.startDate = startDate;
        this.summary = summary;
        this.period = period;
        this.mainImage = mainImage;
        this.subImages = subImages;
        this.price = price;
    }

    public Product toProduct() {
        Product product = Product.builder()
                .title(title)
                .target(target)
                .area(area)
                .theme(theme)
                .startDate(String.join(",", startDate).replaceAll("\"", ""))
                .summary(String.join(",", summary).replaceAll("\"", ""))
                .period(Period.ofDays(period))
                .price(price)
                .build();

        product.setId(id);
        new ProductImage(mainImage, product);
        for (int i = 0; i < subImages.size(); i++) {
            new ProductImage(subImages.get(i).replaceAll("\"", ""), product);
        }

        return product;
    }
}
