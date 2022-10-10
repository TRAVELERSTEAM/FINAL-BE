package com.travelers.biz.repository.reservation;

import com.travelers.dto.ReservationResInfo;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryQuery {

    PagingCorrespondence.Response<ReservationResInfo> findListByMemberId(final Long memberId, final Pageable pageable);
}
