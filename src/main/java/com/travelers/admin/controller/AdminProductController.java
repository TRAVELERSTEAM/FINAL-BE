package com.travelers.admin.controller;

import com.travelers.biz.domain.Product;
import com.travelers.biz.service.ProductService;
import com.travelers.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> productList = productService.getProductAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product: productList) {
            ProductDto productDto = new ProductDto();
            productDto.initProduct(product);
            productDtoList.add(productDto);
        }
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable Long id) {
        Optional<Product> productOpt = productService.getProductDetails(id);
        ProductDto productDto = new ProductDto();
        productOpt.ifPresent(productDto::initProduct);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/products")
    public void register(@RequestBody List<ProductDto> productDto){
        productService.registAll(productDto);
    }

    @PutMapping("/product/{id}")
    public void modifyProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productService.modifyProduct(id, productDto);
    }
}
