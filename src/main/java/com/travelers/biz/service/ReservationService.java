package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.reservation.Reservation;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.ReservationRepository;
import com.travelers.biz.repository.departure.DepartureRepository;
import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.travelers.exception.OptionalHandler.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final MemberRepository memberRepository;
    private final DepartureRepository departureRepository;
    private final ReservationRepository reservationRepository;

    public void create(
            final Long memberId,
            final Long departureId,
            final HeadCount headCount
    ) {
        final Member member = findOrThrow(memberId, memberRepository::findById, ErrorCode.MEMBER_NOT_FOUND);
        final Departure departure = findOrThrow(departureId, departureRepository::findById, ErrorCode.RESOURCE_NOT_FOUND);
        final Reservation reserve = departure.reserve(member, headCount);

        reservationRepository.save(reserve);
    }
}
