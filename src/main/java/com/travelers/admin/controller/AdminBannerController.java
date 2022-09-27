package com.travelers.admin.controller;

import com.travelers.biz.domain.Banner;
import com.travelers.biz.service.BannerService;
import com.travelers.dto.BannerDto;
import com.travelers.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerService bannerService;

    @PostMapping("/banner/loaddata")
    public void loadData() throws IOException {
        bannerService.loadData();
    }

    @PostMapping("/banners")
    public void  register(@RequestBody List<BannerDto> bannerDtoList) {
        bannerService.registAll(bannerDtoList);
    }

    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getBannerList() {
        return ResponseEntity.ok(bannerService.getBannerList());
    }

    @PutMapping("/banner/{id}")
    public void modifyBanner(@PathVariable Long id,@RequestBody BannerDto bannerDto) {
        bannerService.modifyBanner(id, bannerDto);
    }


}
