package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelers.biz.domain.departure.embeddable.When;
import com.travelers.biz.domain.reservation.Reservation;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import lombok.Getter;

public class ReservationRes {

    @Getter
    public static class Info {
        private final String code;
        private final String productName;
        private final String payment;
        private final HeadCount headCount;
        private final When when;
        private final Reservation.Status status;

        @QueryProjection
        public Info(
                final String code,
                final String productName,
                final String payment,
                final HeadCount headCount,
                final When when,
                final Reservation.Status status
        ) {
            this.code = code;
            this.productName = productName;
            this.payment = payment;
            this.headCount = headCount;
            this.when = when;
            this.status = status;
        }
    }
}
