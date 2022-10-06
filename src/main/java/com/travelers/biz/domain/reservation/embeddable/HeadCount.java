package com.travelers.biz.domain.reservation.embeddable;

import com.travelers.biz.domain.product.embeddable.Price;

import javax.persistence.Embeddable;

@Embeddable
public class HeadCount {

    private int adult;
    private int kid;
    private int infant;

    public int getHeadCount() {
        return adult + kid + infant;
    }

    public long calculateFee(final Price price) {
        return adult * price.getAdult()
                + kid * price.getKid()
                + infant * price.getInfant();
    }

}
