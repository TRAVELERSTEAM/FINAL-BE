package com.travelers.biz.service;

import com.travelers.biz.domain.Banner;
import com.travelers.biz.repository.BannerRepository;
import com.travelers.dto.BannerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void addBanner(BannerDto bannerDto) {
        List<Banner> bannerList = new ArrayList<>();
        for (String image : bannerDto.getImage()) {
            Banner banner = Banner.builder()
                    .image(image)
                    .build();
            bannerList.add(banner);
        }
        bannerRepository.saveAll(bannerList);
    }

    public List<Banner> getBannerList() {
        return bannerRepository.findAll();
    }
}
