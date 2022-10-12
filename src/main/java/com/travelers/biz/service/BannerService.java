package com.travelers.biz.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.travelers.biz.domain.Banner;
import com.travelers.biz.repository.BannerRepository;
import com.travelers.dto.BannerDto;
import com.travelers.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    @Transactional
    public void registAll(List<BannerDto> bannerDtoList) {
    }

    @Transactional(readOnly = true)
    public void loadData() throws IOException {
        List<BannerDto> bannerDtoList = new ArrayList<>();
        JsonArray jsonBanner = JsonUtil.getJson("json/banner.json");

    }

    @Transactional(readOnly = true)
    public List<Banner> getBannerList() {
        return bannerRepository.findAll();
    }

    @Transactional
    public void modifyBanner(Long id, BannerDto bannerDto) {

    }

    @Transactional
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }
}
