package com.travelers.admin.controller;

import com.travelers.biz.service.BannerService;
import com.travelers.dto.BannerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kei
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @PostMapping("banner")
    public void  register(@RequestBody BannerDto bannerDto) {
        bannerService.addBanner(bannerDto);
    }
}
