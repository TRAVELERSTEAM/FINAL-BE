package com.travelers.biz.repository.reservation;

import com.travelers.biz.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryQuery {

    @Query(" select r from Reservation r where r.id = :reservationId and r.member.id = :memberId")
    Optional<Reservation> findByIdAndMemberId(@Param("reservationId") final Long reservationId, @Param("memberId") final Long memberId);
}
