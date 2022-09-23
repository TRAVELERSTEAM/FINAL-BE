package com.travelers.dto;

import lombok.*;


/**
 * @author kei
 * @since 2022-09-21
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {
    private String hashtag;
    private String title;
    private String image;
    private Long productId;
}
