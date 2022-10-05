package com.travelers.biz.domain.departure;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.base.BaseTime;
import com.travelers.biz.domain.reservation.Reservation;
import com.travelers.biz.domain.reservation.embeddable.*;
import com.travelers.config.converter.LocalDateConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Departure extends BaseTime {

    public enum Status {
        OPENED, CLOSED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "when_departure")
    private LocalDate whenDeparture;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "when_return")
    private LocalDate whenReturn;

    @Embedded
    @Column(name = "capacity")
    private Capacity capacity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Departure(final Product product, final LocalDate whenDeparture, final Capacity capacity) {
        this.product = product;
        this.whenDeparture = whenDeparture;
        this.whenReturn = whenDeparture.plusDays(product.getPeriod());
        this.capacity = capacity;
    }

    public static Departure create(final Product product, final LocalDate whenDeparture, final Capacity capacity) {
        return new Departure(product, whenDeparture, capacity);
    }

    public Reservation reserve(final Member member, final Adult adult, final Kid kid, final Infant infant){
        calculateCapacity(adult, kid, infant);
        return Reservation.builder()
                .member(member)
                .departure(this)
                .adult(adult)
                .kid(kid)
                .infant(infant)
                .fee(calculateFee(adult, kid, infant))
                .build();
    }

    private int calculateFee(final Adult adult, final Kid kid, final Infant infant) {
        return adult.calculateFee(product.getAdultPrice())
                + kid.calculateFee(product.getKidPrice())
                + infant.calculateFee(product.getInfantPrice());
    }

    private void calculateCapacity(final Tourist... person) {
        Arrays.asList(person)
                .forEach(e -> minusCapacity(e.getHeadCount()));
    }

    private void minusCapacity(final int touristCnt){
        if(this.capacity.minusCapacity(touristCnt) == 0){
            this.status = Status.CLOSED;
        }
    }

    public void plusRemainCapacity(final int touristCnt){
        this.capacity.plusCapacity(touristCnt);
    }

    public int getCapacity(){
        return this.capacity.getMaxCapacity();
    }
}
