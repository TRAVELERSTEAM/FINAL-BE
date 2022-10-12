package com.travelers.admin.controller;

import com.travelers.biz.service.BannerService;
import com.travelers.dto.BannerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerService bannerService;

    @GetMapping("/banner/load")
    public ResponseEntity<Objects> loadData() throws IOException {
        bannerService.loadData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/banners")
    public ResponseEntity<List<BannerResponseDto>> allBanner() {
        return new ResponseEntity<>(bannerService.findAllBanner(), HttpStatus.OK);
    }

    @GetMapping("/banner/{bannerId}")
    public ResponseEntity<BannerResponseDto> oneBanner(@PathVariable Long bannerId) {
        return new ResponseEntity<>(bannerService.findBanner(bannerId), HttpStatus.OK);
    }
}
