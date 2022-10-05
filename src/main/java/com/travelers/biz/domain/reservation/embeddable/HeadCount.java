package com.travelers.biz.domain.reservation.embeddable;

import com.travelers.biz.domain.departure.embeddable.Price;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class HeadCount {

    private int adult;
    private int kid;
    private int infant;

    public int totalHeadCount() {
        return adult + kid + infant;
    }

    public long calculateFee(final Price price) {
        return adult * price.getAdult()
                + kid * price.getKid()
                + infant * price.getInfant();
    }
}
