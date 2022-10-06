package com.travelers.biz.domain.departure.embeddable;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Price {

    private Long adult;
    private Long kid;
    private Long infant;
}
