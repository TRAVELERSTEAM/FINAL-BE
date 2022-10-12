package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travelers.biz.domain.Banner;
import com.travelers.biz.repository.BannerRepository;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.dto.BannerRequestDto;
import com.travelers.dto.BannerResponseDto;
import com.travelers.exception.TravelersException;
import com.travelers.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.exception.ErrorCode.BANNER_NOT_FOUND;
import static com.travelers.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;

    // 배너 넣기
    @Transactional
    public void loadData() throws IOException {
        JsonArray jsonBanner = JsonUtil.getJson("json/banner.json");
        for (int i = 0; i < jsonBanner.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonBanner.get(i);
            BannerRequestDto bannerRequestDto = BannerRequestDto.builder()
                    .hashtag(jsonObject.get("hashtag").getAsString())
                    .title(jsonObject.get("title").getAsString())
                    .image(jsonObject.get("image").getAsString())
                    .productId(jsonObject.get("productId").getAsLong())
                    .build();
            Banner banner = bannerRequestDto.toBanner();
            banner.setProduct(productRepository.findById(bannerRequestDto.getProductId())
                    .orElseThrow(() -> new TravelersException(PRODUCT_NOT_FOUND)));
            bannerRepository.save(banner);
        }
    }

    // 배너 전체 목록 조회
    @Transactional(readOnly = true)
    public List<BannerResponseDto> findAllBanner() {
        return bannerRepository.findAll()
                .stream().map(BannerResponseDto::of)
                .collect(Collectors.toList());
    }

    // 배너 목록 조회
    @Transactional(readOnly = true)
    public BannerResponseDto findBanner(Long bannerId) {
        Banner banner =  bannerRepository.findById(bannerId)
                .orElseThrow(() -> new TravelersException(BANNER_NOT_FOUND));
        return BannerResponseDto.of(banner);
    }

}
