package com.travelers.biz.domain.reservation.embeddable;

public abstract class Tourist {

    protected int checkHeadCount(final int headCount){
        return Math.max(headCount, 0);
    }

    public abstract int getHeadCount();

    public int calculateFee(final int fee) {
        return getHeadCount() * fee;
    }
}
