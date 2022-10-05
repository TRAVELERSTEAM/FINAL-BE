package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

public class DepartureResponse {

    @Getter
    public static class TravelPeriod {
        private final LocalDate whenDeparture;
        private final LocalDate whenReturn;

        @QueryProjection
        public TravelPeriod(
                final LocalDate whenDeparture,
                final LocalDate whenReturn
        ) {
            this.whenDeparture = whenDeparture;
            this.whenReturn = whenReturn;
        }
    }
}
