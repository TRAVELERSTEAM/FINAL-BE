package com.travelers.biz.repository.reservation;

import com.travelers.biz.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryQuery {
}
