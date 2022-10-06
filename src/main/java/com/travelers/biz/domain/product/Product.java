package com.travelers.biz.domain.product;

import com.travelers.biz.domain.product.embeddable.Price;
import com.travelers.config.converter.PeriodConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Period;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = PeriodConverter.class)
    private Period period;

    @Embedded
    private Price price;

    private String name;

    public int getPeriod() {
        return period.getDays();
    }

}
