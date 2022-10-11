package com.travelers.biz.service;

import com.travelers.biz.domain.product.Product;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.dto.ProductResponseDto;
import com.travelers.exception.TravelersException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 공용
    // 제품 전체목록 보기
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProduct() {
        return productRepository.findAll()
                .stream().map(ProductResponseDto::of).collect(Collectors.toList());
    }

    // 제품 상세보기
    @Transactional(readOnly = true)
    public ProductResponseDto findProduct(Long ProductId) {
        Product product = productRepository.findById(ProductId)
                .orElseThrow(() -> new TravelersException(PRODUCT_NOT_FOUND));
        return ProductResponseDto.of(product);
    }

    /*****************************************************************************/


}
