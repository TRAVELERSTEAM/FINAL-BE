package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travelers.biz.domain.Banner;
import com.travelers.biz.repository.BannerRepository;
import com.travelers.dto.BannerDto;
import com.travelers.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-06
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {
    private final BannerRepository bannerRepository;

    public void registAll(List<BannerDto> bannerDtoList) {
        List<Banner> bannerList = new ArrayList<>();
        for(BannerDto bannerDto: bannerDtoList) {
            Banner banner = Banner.builder()
                    .hashtag(bannerDto.getHashtag())
                    .title(bannerDto.getTitle())
                    .image(bannerDto.getImage())
                    .productId(bannerDto.getProductId())
                    .build();
            bannerList.add(banner);
        }
        bannerRepository.saveAll(bannerList);
    }

    public void loadData() throws IOException {
        List<BannerDto> bannerDtoList = new ArrayList<>();
        JsonArray jsonBanner = JsonUtil.getJson("json/banner.json");
        for (int i = 0; i < jsonBanner.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonBanner.get(i);
            BannerDto bannerDto = BannerDto.builder()
                    .hashtag(jsonObject.get("hashtag").getAsString())
                    .title(jsonObject.get("title").getAsString())
                    .image(jsonObject.get("image").getAsString())
                    .productId(jsonObject.get("productId").getAsLong())
                    .build();
            bannerDtoList.add(bannerDto);
        }
        registAll(bannerDtoList);
    }

    public List<Banner> getBannerList() {
        return bannerRepository.findAll();
    }

    public void modifyBanner(Long id, BannerDto bannerDto) {
        bannerRepository.findById(id).ifPresent(banner -> {
            banner.setHashtag(bannerDto.getHashtag());
            banner.setTitle(bannerDto.getTitle());
            banner.setImage(bannerDto.getImage());
            banner.setProductId(bannerDto.getProductId());
        });
    }

    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }
}
