package com.travelers.dto;

import com.travelers.biz.domain.departure.embeddable.When;
import com.travelers.biz.domain.reservation.AnonymousReservation;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import lombok.Getter;

@Getter
public class AnonymousReservationResInfo {

    private final String code;
    private final String productName;
    private final Long payment;
    private final HeadCount headCount;
    private final When when;
    private final AnonymousReservation.Status status;

    public AnonymousReservationResInfo(
            final String code,
            final String productName,
            final Long payment,
            final HeadCount headCount,
            final When when,
            final AnonymousReservation.Status status
    ) {
        this.code = code;
        this.productName = productName;
        this.payment = payment;
        this.headCount = headCount;
        this.when = when;
        this.status = status;
    }
}
