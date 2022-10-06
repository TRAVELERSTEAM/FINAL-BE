package com.travelers.biz.repository.reservation;

import com.travelers.biz.domain.QProduct;
import com.travelers.biz.domain.departure.QDeparture;
import com.travelers.biz.domain.reservation.QReservation;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.QReservationResInfo;
import com.travelers.dto.ReservationResInfo;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.travelers.biz.domain.QProduct.product;
import static com.travelers.biz.domain.departure.QDeparture.departure;
import static com.travelers.biz.domain.reservation.QReservation.reservation;

public class ReservationRepositoryImpl extends QuerydslSupports implements ReservationRepositoryQuery {

    private static final QReservation R = reservation;

    public ReservationRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public PagingCorrespondence.Response<ReservationResInfo> findListByMemberId(final Long memberId, final Pageable pageable) {
        return PagingCorrespondence.Response.from(
                applyPagination(pageable,
                        () -> select(new QReservationResInfo(
                                R.code,
                                product.name.as("productName"),
                                R.payment,
                                R.headCount,
                                R.when,
                                R.status
                        ))
                                .from(R)
                                .where(R.member.id.eq(memberId))
                                .join(R.departure, departure)
                                .join(departure.product, product)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize()),
                        select(R.id.count())
                                .from(R)
                                .where(R.member.id.eq(memberId))
                ));
    }
}
