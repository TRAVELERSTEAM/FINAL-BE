package com.travelers.dto;

import com.travelers.biz.domain.Banner;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerResponseDto {
    private String hashtag;
    private String title;
    private String image;
    private Long productId;

    public static BannerResponseDto of(Banner banner) {
        return new BannerResponseDto(
                banner.getHashtag(),
                banner.getTitle(),
                banner.getImage(),
                banner.getProduct().getId()
        );
    }
}
