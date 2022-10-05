package com.travelers.biz.repository.departure;

import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.departure.QDeparture;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.DepartureResponse;
import com.travelers.dto.QDepartureResponse_TravelPeriod;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                        D.whenDeparture,
                        D.whenReturn))
                .from(D)
                .where(D.product.id.eq(productId)
                        .and(D.status.eq(Departure.Status.OPENED)))
                .fetch();
    }

//    @Override
//    public Optional<Departure> findByDepartureId(final Long departureId) {
//        return select(D)
//                .from(D)
//                .where(D.id.eq(departureId));
//    }
}
