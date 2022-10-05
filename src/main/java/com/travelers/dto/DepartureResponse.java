package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelers.biz.domain.departure.embeddable.Price;
import com.travelers.biz.domain.reservation.embeddable.Capacity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

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

    @Getter
    public static class ReservationInfo {
        private final Long id;
        private final String productName;
        private final Period period;
        private final Price price;
        private final Capacity capacity;

        @QueryProjection
        public ReservationInfo(
                final Long id,
                final String productName,
                final Period period,
                final Price price,
                final Capacity capacity
        ) {
            this.id = id;
            this.productName = productName;
            this.period = period;
            this.price = price;
            this.capacity = capacity;
        }
    }
}
