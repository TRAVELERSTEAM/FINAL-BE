package com.travelers.biz.repository;

import com.travelers.biz.domain.notify.Notify;
import com.travelers.biz.repository.query.NotifyRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long>, NotifyRepositoryQuery {
}
