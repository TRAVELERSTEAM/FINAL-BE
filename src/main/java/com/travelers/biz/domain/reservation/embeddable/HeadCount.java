package com.travelers.biz.domain.reservation.embeddable;

import com.travelers.biz.domain.product.embeddable.Price;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HeadCount {

    @Column(name = "adult")
    private int adult;
    @Column(name = "kid")
    private int kid;
    @Column(name = "infant")
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
