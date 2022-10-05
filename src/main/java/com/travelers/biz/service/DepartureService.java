package com.travelers.biz.service;

import com.travelers.biz.repository.departure.DepartureRepository;
import com.travelers.dto.DepartureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartureService {

    private final DepartureRepository departureRepository;

    public List<DepartureResponse.TravelPeriod> availableReservationList(final Long productId) {
        return departureRepository.availableReservationListBy(productId);
    }
}
