package com.travelers.biz.domain.reservation;

import com.travelers.biz.domain.AnonymousMember;
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
public class AnonymousReservation {

    public enum Status {
        STANDBY, CANCELED, COMPLETION, TRAVEL_COMPLETION
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anonymous_member_id")
    private AnonymousMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_id")
    private Departure departure;

    @Embedded
    private HeadCount headCount;

    @Column(name = "payment")
    private long payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnonymousReservation.Status status;

    @Embedded
    private When when;

    @Column(name = "code")
    private String code;

    @Builder
    private AnonymousReservation(
            final AnonymousMember member,
            final Departure departure,
            final HeadCount headCount,
            final long payment
    ) {
        this.member = member;
        this.departure = departure;
        this.headCount = headCount;
        this.payment = payment;
        this.when = departure.getWhen();
        this.status = AnonymousReservation.Status.STANDBY;
        createRandomCode();
    }

    private void createRandomCode() {
        this.code = randomCode();
        member.addReservationCode(code);
    }

    private String randomCode() {
        return UUID.randomUUID().toString().substring(0, 12);
    }

    public void cancel() {
        this.status = AnonymousReservation.Status.CANCELED;
        this.departure.plusRemainCapacity(headCount.getTotalCount());
    }

}
