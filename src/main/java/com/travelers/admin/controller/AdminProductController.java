package com.travelers.admin.controller;

import com.travelers.biz.domain.Product;
import com.travelers.biz.service.ProductService;
import com.travelers.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProductAll());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetails(id).get());
    }

    @PostMapping("/product")
    public void register(@RequestBody List<ProductDto> productDto){
        productService.registAll(productDto);
    }
}
