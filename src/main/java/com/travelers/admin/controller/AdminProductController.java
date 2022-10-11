package com.travelers.admin.controller;

import com.travelers.biz.service.ProductService;
import com.travelers.dto.ProductRequestDto;
import com.travelers.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProductController {

    private final ProductService productService;

    // 제품 전체목록 보기
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> allProduct() {
        return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);
    }

    // 제품 상세 보기
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponseDto> oneProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.findProduct(productId), HttpStatus.OK);
    }


}
