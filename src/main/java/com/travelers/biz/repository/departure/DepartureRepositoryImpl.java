package com.travelers.biz.repository.departure;

import com.travelers.biz.domain.QProduct;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.departure.QDeparture;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.DepartureResponse;
import com.travelers.dto.QDepartureResponse_ReservationInfo;
import com.travelers.dto.QDepartureResponse_TravelPeriod;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.travelers.biz.domain.QProduct.product;
import static com.travelers.biz.domain.departure.QDeparture.departure;

public class DepartureRepositoryImpl extends QuerydslSupports implements DepartureRepositoryQuery {

    private static final QDeparture D = departure;

    protected DepartureRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<DepartureResponse.TravelPeriod> availableReservationListBy(final Long productId) {
        return select(
                new QDepartureResponse_TravelPeriod(
                        D.id,
                        D.whenDeparture,
                        D.whenReturn))
                .from(D)
                .where(D.product.id.eq(productId)
                        .and(D.status.eq(Departure.Status.OPENED)))
                .fetch();
    }

    @Override
    public Optional<DepartureResponse.ReservationInfo> findForReservation(final Long departureId) {
        return Optional.ofNullable(
                select(new QDepartureResponse_ReservationInfo(
                        product.id,
                        D.id,
                        product.name,
                        product.period,
                        product.price,
                        D.capacity
                ))
                .from(D)
                .join(D.product, product)
                .where(D.id.eq(departureId))
                .fetchOne());
    }
}
