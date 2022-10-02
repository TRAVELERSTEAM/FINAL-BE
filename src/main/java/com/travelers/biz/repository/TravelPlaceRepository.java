package com.travelers.biz.repository;

import com.travelers.biz.domain.TravelPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlaceRepository extends JpaRepository<TravelPlace, Long> {
}
