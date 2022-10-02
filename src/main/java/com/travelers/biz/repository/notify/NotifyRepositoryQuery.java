package com.travelers.biz.repository.notify;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NotifyRepositoryQuery {

    PagingCorrespondence.Response<NotifyResponse.SimpleInfo> findSimpleList(final NotifyType notifyType, final Pageable pageable);
    Optional<NotifyResponse.DetailInfo> findDetail(final Long id, final NotifyType notifyType);
}
