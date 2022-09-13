package com.travelers.biz.service;

import com.travelers.biz.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author kei
 * @since 2022-09-06
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
