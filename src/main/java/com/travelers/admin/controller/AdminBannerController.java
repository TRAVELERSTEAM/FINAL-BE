package com.travelers.admin.controller;

import com.travelers.biz.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerService bannerService;

    @PostMapping("/banner/load")
    public void loadData() throws IOException {
        bannerService.loadData();
    }

}
