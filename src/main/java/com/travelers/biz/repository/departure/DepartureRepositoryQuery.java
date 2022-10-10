package com.travelers.biz.repository.departure;

import com.travelers.dto.DepartureResponse;

import java.util.List;
import java.util.Optional;

public interface DepartureRepositoryQuery {

    List<DepartureResponse.TravelPeriod> availableReservationListBy(final Long productId);
    Optional<DepartureResponse.ReservationInfo> findForReservation(final Long departureId);
}
