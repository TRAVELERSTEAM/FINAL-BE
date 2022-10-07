package com.travelers.biz.domain.reservation.embeddable;

import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Capacity {

    @Column(name = "max_capacity")
    private int maxCapacity;
    @Column(name = "min_capacity")
    private int minCapacity;
    @Column(name = "cur_capacity")
    private int curCapacity;
    @Column(name = "remain_capacity")
    private int remainCapacity;

    public Capacity(final int maxCapacity, final int minCapacity) {
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
    }

    public int minusCapacity(final int touristCnt){
        if(remainCapacity < touristCnt) {
            throw new TravelersException(ErrorCode.CANT_RESERVE_TRAVEL);
        }
        return calculateCapacity(touristCnt);
    }

    private int calculateCapacity(final int touristCnt) {
        curCapacity = touristCnt;
        return remainCapacity = maxCapacity -  touristCnt;
    }

    public void plusCapacity(final int touristCnt){
        curCapacity -= touristCnt;
        remainCapacity += touristCnt;
    }
}
