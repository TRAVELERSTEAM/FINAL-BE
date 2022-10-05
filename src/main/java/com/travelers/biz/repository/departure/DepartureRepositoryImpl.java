package com.travelers.biz.repository.departure;

import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.departure.QDeparture;
import com.travelers.biz.repository.notify.config.QuerydslSupports;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.travelers.biz.domain.departure.QDeparture.departure;

public class DepartureRepositoryImpl extends QuerydslSupports implements DepartureRepositoryQuery {

    private static final QDeparture D = departure;

    protected DepartureRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<LocalDate> findAllByProductId(Long productId) {
        return select(D.whenDeparture)
                .from(D)
                .where(D.product.id.eq(productId)
                        .and(D.status.eq(Departure.Status.OPENED)))
                .fetch();
    }
}
