package com.travelers.biz.domain.reservation.embeddable;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Kid extends Tourist {

    private int kidCount;

    public Kid(final int kidCount) {
        this.kidCount = checkHeadCount(kidCount);
    }

    @Override
    public int getHeadCount() {
        return kidCount;
    }

}
