package com.travelers.biz.repository.notify;

import com.travelers.biz.domain.notify.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotifyRepository extends JpaRepository<Notify, Long>, NotifyRepositoryQuery {

    @Query(" select n.sequence from Notify n where n.notifyType = Notice order by n.id desc ")
    Optional<Notify> initNotice();

    @Query(" select n.sequence from Notify n where n.notifyType = RefLibrary order by n.id desc ")
    Optional<Notify> initRefLibrary();
}
