package com.travelers.admin.controller;

import com.travelers.biz.domain.Product;
import com.travelers.biz.service.ProductService;
import com.travelers.dto.ProductDto;
import com.travelers.storage.FileDetail;
import com.travelers.storage.FileUploadService;
import com.travelers.storage.FileWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author kei
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @PostMapping("/product/loaddata")
    public void loadData() throws IOException {
        productService.loadData();
    }


}
