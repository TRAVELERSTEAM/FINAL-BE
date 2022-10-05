package com.travelers.biz.domain.reservation;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.departure.When;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    public enum Status {
        STANDBY, CANCEL, COMPLETION, TRAVEL_COMPLETION
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id")
    private Departure departure;

    @Embedded
    private HeadCount headCount;

    @Column(name = "fee")
    private long fee;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private When when;

    private String reservationCode;

    @Builder
    private Reservation(
            final Member member,
            final Departure departure,
            final HeadCount headCount,
            final long fee,
            final String reservationCode
    ) {
        this.member = member;
        this.departure = departure;
        this.headCount = headCount;
        this.fee = fee;
        this.when = departure.getWhen();
        this.status = Status.STANDBY;
        this.reservationCode = reservationCode;
    }

    public void cancel() {
        this.status = Status.CANCEL;
    }
}

