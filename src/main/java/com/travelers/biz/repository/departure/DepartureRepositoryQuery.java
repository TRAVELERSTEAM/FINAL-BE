package com.travelers.biz.repository.departure;

import java.time.LocalDate;
import java.util.List;

public interface DepartureRepositoryQuery {

    List<LocalDate> availableReservationBy(final Long productId);
}
