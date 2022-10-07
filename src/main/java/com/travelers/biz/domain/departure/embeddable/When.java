package com.travelers.biz.domain.departure.embeddable;

import com.travelers.config.converter.LocalDateConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.Period;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class When {

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "when_departure")
    private LocalDate whenDeparture;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "when_return")
    private LocalDate whenReturn;

    public When(final LocalDate whenDeparture, final int days) {
        this.whenDeparture = whenDeparture;
        this.whenReturn = whenDeparture.plusDays(days);
    }
}
