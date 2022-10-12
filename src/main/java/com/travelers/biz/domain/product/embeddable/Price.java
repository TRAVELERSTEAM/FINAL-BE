package com.travelers.biz.domain.product.embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {

    private Long adult;
    private Long kid;
    private Long infant;

    @Builder
    public Price(Long adult, Long kid, Long infant) {
        this.adult = adult;
        this.kid = kid;
        this.infant = infant;
    }
}
