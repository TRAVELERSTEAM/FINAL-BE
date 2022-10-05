package com.travelers.biz.domain.reservation.embeddable;

import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Capacity {

    private int maxCapacity;
    private int remainCapacity;

    public Capacity(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int minusCapacity(final int touristCnt){
        if(remainCapacity < touristCnt) {
            throw new TravelersException(ErrorCode.CANT_RESERVE_TRAVEL);
        }
        return remainCapacity -= touristCnt;
    }

    public void plusCapacity(final int touristCnt){
        this.remainCapacity += touristCnt;
    }
}
