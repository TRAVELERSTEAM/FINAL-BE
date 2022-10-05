package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

public class DepartureResponse {

    @Getter
    public static class TravelPeriod {
        private final Long departureId;
        private final LocalDate whenDeparture;
        private final LocalDate whenReturn;

        @QueryProjection
        public TravelPeriod(
                final Long departureId,
                final LocalDate whenDeparture,
                final LocalDate whenReturn
        ) {
            this.departureId = departureId;
            this.whenDeparture = whenDeparture;
            this.whenReturn = whenReturn;
        }
    }
}
