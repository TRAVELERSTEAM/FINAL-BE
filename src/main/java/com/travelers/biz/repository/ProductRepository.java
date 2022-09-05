package com.travelers.biz.repository;

import com.travelers.biz.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kei
 * @since 2022-09-06
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
