package com.travelers.biz.repository;

import com.travelers.biz.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    Optional<Banner> findById(Long bannerId);
}
