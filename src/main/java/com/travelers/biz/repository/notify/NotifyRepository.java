package com.travelers.biz.repository.notify;

import com.travelers.biz.domain.notify.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotifyRepository extends JpaRepository<Notify, Long>, NotifyRepositoryQuery {

    @Query(" select n from Notify n where n.notifyType = Notice and n.sequence = (select nn.sequence from Notice nn where n.notifyType = Notice ) ")
    Optional<Notify> initNotice();

    @Query(" select n from Notify n where n.notifyType = RefLibrary and n.sequence = (select nn.sequence from Notice nn where n.notifyType = RefLibrary )")
    Optional<Notify> initRefLibrary();
}
