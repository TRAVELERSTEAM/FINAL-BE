package com.travelers.biz.repository;

import com.travelers.biz.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kei
 * @since 2022-09-06
 */
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    void deleteByProductIdAndImage(Long productId, String image);
}
