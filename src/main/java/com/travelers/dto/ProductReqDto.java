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
public class ProductReqDto {
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
}
