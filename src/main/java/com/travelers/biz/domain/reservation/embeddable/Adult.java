package com.travelers.biz.domain.reservation.embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Adult extends Tourist {

    private int adultCount;

    public Adult(final int adultCount) {
        this.adultCount = checkHeadCount(adultCount);
    }

    @Override
    public int getHeadCount() {
        return adultCount;
    }

}
