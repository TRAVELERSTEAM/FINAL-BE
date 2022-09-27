package com.travelers.biz.repository;

import com.travelers.biz.domain.ProductStartDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kei
 * @since 2022-09-06
 */
@Repository
public interface ProductStartDateRepository extends JpaRepository<ProductStartDate, Long> {
    void deleteByIdAndStartDate(Long id, Integer startDate);
}
