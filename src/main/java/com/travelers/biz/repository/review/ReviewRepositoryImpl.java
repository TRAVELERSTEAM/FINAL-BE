package com.travelers.biz.repository.review;

import com.travelers.biz.repository.notify.config.QuerydslSupports;

import javax.persistence.EntityManager;

public class ReviewRepositoryImpl extends QuerydslSupports implements ReviewRepositoryQuery{

    public ReviewRepositoryImpl(EntityManager em) {
        super(em);
    }
}
