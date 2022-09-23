package com.travelers.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-06
 */
@Data
@Builder
public class ProductDto {
    private String title;
    private Integer price;
    private String thumbnail;
    private String target;
    private String destination;
    private String theme;
    @Builder.Default
    private List<Integer> startDate = new ArrayList<>();
    @Builder.Default
    private List<String> image = new ArrayList<>();
}
