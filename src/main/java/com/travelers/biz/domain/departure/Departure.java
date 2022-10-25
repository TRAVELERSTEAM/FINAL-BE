package com.travelers.biz.domain.departure;

import com.travelers.biz.domain.AnonymousMember;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.base.BaseTime;
import com.travelers.biz.domain.departure.embeddable.When;
import com.travelers.biz.domain.product.Product;
import com.travelers.biz.domain.reservation.AnonymousReservation;
import com.travelers.biz.domain.reservation.Reservation;
import com.travelers.biz.domain.reservation.embeddable.Capacity;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Departure extends BaseTime {

    public enum Status {
        OPENED, CLOSED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departure_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    private Capacity capacity;

    @Embedded
    private When when;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    private Departure(final Product product, final LocalDate whenDeparture, final Capacity capacity) {
        this.product = product;
        this.when = new When(whenDeparture, product.getPeriod());
        this.capacity = capacity;
    }

    public static Departure create(final Product product, final LocalDate whenDeparture, final Capacity capacity) {
        return new Departure(product, whenDeparture, capacity);
    }

    public Reservation reserve(final Member member, final HeadCount headCount){
        minusCapacity(headCount.getTotalCount());
        return Reservation.builder()
                .member(member)
                .departure(this)
                .headCount(headCount)
                .payment(calculateFee(headCount))
                .build();
    }

    public AnonymousReservation reserve(final AnonymousMember anonymousMember, final HeadCount headCount) {
        minusCapacity(headCount.getTotalCount());
        return AnonymousReservation
                .builder()
                .member(anonymousMember)
                .departure(this)
                .headCount(headCount)
                .payment(calculateFee(headCount))
                .build();
    }

    private long calculateFee(final HeadCount headCount) {
        return headCount.calculateFee(product.getPrice());
    }

    private void minusCapacity(final int touristCnt){
        if(this.capacity.minusCapacity(touristCnt) == 0){
            this.status = Status.CLOSED;
        }
    }

    public void plusRemainCapacity(final int touristCnt){
        this.capacity.plusCapacity(touristCnt);
    }

}
