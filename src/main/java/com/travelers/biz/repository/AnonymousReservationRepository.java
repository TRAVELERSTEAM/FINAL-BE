package com.travelers.biz.repository;

import com.travelers.biz.domain.reservation.AnonymousReservation;
import com.travelers.dto.AnonymousReservationResInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnonymousReservationRepository extends JpaRepository<AnonymousReservation, Long> {

    @Query("  select new " +
            " com.travelers.dto.AnonymousReservationResInfo(ar.code, p.name, ar.payment, ar.headCount, ar.when, ar.status) " +
            " from AnonymousReservation ar " +
            " join ar.departure d " +
            " join d.product p " +
            " where ar.code = :reservationCode")
    Optional<AnonymousReservationResInfo> findByCode(@Param("reservationCode") final String reservationCode);
}
