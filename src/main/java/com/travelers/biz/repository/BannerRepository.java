package com.travelers.biz.repository;

import com.travelers.biz.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kei
 * @since 2022-09-06
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}
