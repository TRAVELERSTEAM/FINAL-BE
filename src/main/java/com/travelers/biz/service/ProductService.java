package com.travelers.biz.service;

import com.travelers.biz.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author kei
 * @since 2022-09-06
 */
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
