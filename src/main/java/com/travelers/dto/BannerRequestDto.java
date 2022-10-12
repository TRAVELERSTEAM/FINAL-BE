package com.travelers.dto;

import com.travelers.biz.domain.Banner;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerRequestDto {
    private String hashtag;
    private String title;
    private String image;
    private Long productId;

    @Builder
    public BannerRequestDto(String hashtag, String title, String image, Long productId) {
        this.hashtag = hashtag;
        this.title = title;
        this.image = image;
        this.productId = productId;
    }

    public Banner toBanner() {
        Banner banner = Banner.builder()
                .hashtag(hashtag)
                .title(title)
                .image(image)
                .build();
        return banner;
    }
}
