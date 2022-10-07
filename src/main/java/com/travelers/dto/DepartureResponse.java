package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelers.biz.domain.product.embeddable.Price;
import com.travelers.biz.domain.departure.embeddable.When;
import com.travelers.biz.domain.reservation.embeddable.Capacity;
import lombok.Getter;

import java.time.Period;

public class DepartureResponse {

    @Getter
    public static class TravelPeriod {
        private final Long departureId;
        private final When when;

        @QueryProjection
        public TravelPeriod(
                final Long departureId,
                final When when
        ) {
            this.departureId = departureId;
            this.when = when;
        }
    }

    @Getter
    public static class ReservationInfo {
        private final Long productId;
        private final Long departureId;
        private final String productName;
        private final int period;
        private final Price price;
        private final Capacity capacity;

        @QueryProjection
        public ReservationInfo(
                final Long productId,
                final Long departureId,
                final String productName,
                final Period period,
                final Price price,
                final Capacity capacity
        ) {
            this.productId = productId;
            this.departureId = departureId;
            this.productName = productName;
            this.period = period.getDays();
            this.price = price;
            this.capacity = capacity;
        }
    }
}
