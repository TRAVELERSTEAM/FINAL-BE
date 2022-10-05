package com.travelers.biz.domain.reservation.embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Infant extends Tourist {

    private int infantCount;

    public Infant(final int infantCount) {
        this.infantCount = checkHeadCount(infantCount);
    }

    @Override
    public int getHeadCount() {
        return infantCount;
    }
}
