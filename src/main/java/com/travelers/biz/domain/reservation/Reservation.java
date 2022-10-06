package com.travelers.biz.domain.reservation;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.departure.embeddable.When;
import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    public enum Status {
        STANDBY, CANCELED, COMPLETION, TRAVEL_COMPLETION
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

    @Column(name = "payment")
    private long payment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private When when;

    private String code;

    @Builder
    private Reservation(
            final Member member,
            final Departure departure,
            final HeadCount headCount,
            final long payment
    ) {
        this.member = member;
        this.departure = departure;
        this.headCount = headCount;
        this.payment = payment;
        this.when = departure.getWhen();
        this.status = Status.STANDBY;
        this.code = randomCode();
    }

    private String randomCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public void cancel() {
        this.status = Status.CANCELED;
        this.departure.plusRemainCapacity(headCount.getHeadCount());
    }
}

