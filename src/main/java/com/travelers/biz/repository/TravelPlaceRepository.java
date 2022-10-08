package com.travelers.biz.repository;

import com.travelers.biz.domain.TravelPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TravelPlaceRepository extends JpaRepository<TravelPlace, Long> {

    @Query("select tp from TravelPlace tp where tp.member.id = :memberId and tp.product.id = :productId")
    Optional<TravelPlace> existsPlace(@Param("memberId") final Long memberId, @Param("productId") final Long productId);
}
