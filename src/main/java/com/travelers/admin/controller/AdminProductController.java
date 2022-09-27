package com.travelers.admin.controller;

import com.travelers.biz.domain.Product;
import com.travelers.biz.service.ProductService;
import com.travelers.dto.ProductReqDto;
import com.travelers.dto.ProductResDto;
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
    public ResponseEntity<List<ProductResDto>> getProducts() {
        List<Product> productList = productService.getProductAll();
        List<ProductResDto> productResDtoList = new ArrayList<>();
        for (Product product: productList) {
            ProductResDto productResDto = new ProductResDto();
            productResDto.initProduct(product);
            productResDtoList.add(productResDto);
        }
        return ResponseEntity.ok(productResDtoList);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResDto> getProductDetails(@PathVariable Long id) {
        Optional<Product> productOpt = productService.getProductDetails(id);
        ProductResDto productResDto = new ProductResDto();
        productOpt.ifPresent(productResDto::initProduct);
        return ResponseEntity.ok(productResDto);
    }

    @PostMapping("/products")
    public void register(@RequestBody List<ProductReqDto> productReqDto){
        productService.registAll(productReqDto);
    }

    @PutMapping("/product/{id}")
    public void modifyProduct(@PathVariable Long id, @RequestBody ProductReqDto productReqDto) {
        productService.modifyProduct(id, productReqDto);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @DeleteMapping("/product/{id}/startdate")
    public void deleteProduct(@PathVariable Long id, @RequestBody Integer startDate) {
        productService.deleteProductStartDate(id, startDate);
    }

    @DeleteMapping("/product/{id}/image")
    public void deleteProduct(@PathVariable Long id, @RequestBody String image) {
        productService.deleteProductImage(id, image);
    }
}
