package com.travelers.biz.repository;

import com.travelers.biz.domain.reservation.AnonymousReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousReservationRepository extends JpaRepository<AnonymousReservation, Long> {
}
