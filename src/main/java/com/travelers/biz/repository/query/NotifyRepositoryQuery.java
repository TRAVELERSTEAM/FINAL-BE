package com.travelers.biz.repository.query;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.PagingResponse;
import org.springframework.data.domain.Pageable;

public interface NotifyRepositoryQuery {
    PagingResponse<NotifyResponse.SimpleInfo> findSimpleList(final NotifyType notifyType, final Pageable pageable);
}
