package com.travelers.biz.service;

import com.travelers.biz.repository.departure.DepartureRepository;
import com.travelers.dto.DepartureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.travelers.exception.OptionalHandler.findWithNotfound;

@Service
@RequiredArgsConstructor
public class DepartureService {

    private final DepartureRepository departureRepository;

    public List<DepartureResponse.TravelPeriod> availableReservationList(final Long productId) {
        return departureRepository.availableReservationListBy(productId);
    }

    public DepartureResponse.ReservationInfo findDeparture(final Long departureId) {
        return findWithNotfound(() -> departureRepository.findForReservation(departureId));
    }
}
