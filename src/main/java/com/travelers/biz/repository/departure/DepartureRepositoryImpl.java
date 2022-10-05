package com.travelers.biz.repository.departure;

import com.travelers.biz.repository.notify.config.QuerydslSupports;

import javax.persistence.EntityManager;

public class DepartureRepositoryImpl extends QuerydslSupports implements DepartureRepositoryQuery {

    protected DepartureRepositoryImpl(EntityManager em) {
        super(em);
    }
}
