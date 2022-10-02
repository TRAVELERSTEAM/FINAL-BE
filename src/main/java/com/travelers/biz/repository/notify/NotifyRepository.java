package com.travelers.biz.repository.notify;

import com.travelers.biz.domain.notify.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long>, NotifyRepositoryQuery {
}
