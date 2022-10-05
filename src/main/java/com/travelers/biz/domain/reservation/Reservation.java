package com.travelers.biz.domain.reservation;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.reservation.embeddable.Adult;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.reservation.embeddable.Infant;
import com.travelers.biz.domain.reservation.embeddable.Kid;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id")
    private Departure departure;

    @Embedded
    @Column(name = "adult_count")
    private Adult adult;

    @Embedded
    @Column(name = "kids_count")
    private Kid kid;

    @Embedded
    @Column(name = "infant_count")
    private Infant infant;

    @Column(name = "fee")
    private int fee;

    @Builder
    private Reservation(
            final Member member,
            final Departure departure,
            final Adult adult,
            final Kid kid,
            final Infant infant,
            final int fee
    ) {
        this.member = member;
        this.departure = departure;
        this.adult = adult;
        this.kid = kid;
        this.infant = infant;
        this.fee = fee;
    }

}

