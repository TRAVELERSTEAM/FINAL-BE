package com.travelers.biz.repository.departure;

import com.travelers.dto.DepartureResponse;

import java.time.LocalDate;
import java.util.List;

public interface DepartureRepositoryQuery {

    List<DepartureResponse.TravelPeriod> availableReservationListBy(final Long productId);
}
