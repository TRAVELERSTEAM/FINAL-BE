package com.travelers.api.controller;

import com.travelers.biz.service.BannerService;
import com.travelers.dto.BannerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @GetMapping("/banners")
    public ResponseEntity<List<BannerResponseDto>> allBanner() {
        return new ResponseEntity<>(bannerService.findAllBanner(), HttpStatus.OK);
    }

    @GetMapping("/banner/{bannerId}")
    public ResponseEntity<BannerResponseDto> oneBanner(@PathVariable Long bannerId) {
        return new ResponseEntity<>(bannerService.findBanner(bannerId), HttpStatus.OK);
    }
}
