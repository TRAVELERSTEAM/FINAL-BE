package com.travelers.biz.domain.reservation.embeddable;

import com.travelers.biz.domain.product.embeddable.Price;
import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.PositiveOrZero;

@Embeddable
@NoArgsConstructor
public class HeadCount {

    @PositiveOrZero
    @Column(name = "adult")
    private int adult;

    @PositiveOrZero
    @Column(name = "kid")
    private int kid;

    @PositiveOrZero
    @Column(name = "infant")
    private int infant;

    public int getTotalCount() {
        if((adult + kid + infant) <= 0) {
            throw new TravelersException(ErrorCode.HEAD_COUNT_MUST_GRATER_THEN_ZERO);
        }
        return adult + kid + infant;
    }

    public long calculateFee(final Price price) {
        return adult * price.getAdult()
                + kid * price.getKid()
                + infant * price.getInfant();
    }

}
