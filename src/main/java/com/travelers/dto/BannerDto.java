package com.travelers.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-21
 */
@Data
@Builder
public class BannerDto {
    private String hashtag;
    private String title;
    private String image;
    private Long productId;
}
