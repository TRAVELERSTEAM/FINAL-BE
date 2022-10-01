package com.travelers.biz.service;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.repository.NotifyRepository;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyRepository notifyRepository;

    public PagingCorrespondence.Response<NotifyResponse.SimpleInfo> showNotifies(
            final NotifyType notifyType,
            final PagingCorrespondence.Request pagingInfo
    ){
        return notifyRepository.findSimpleList(notifyType, pagingInfo.toPageable());
    }

    public NotifyResponse.DetailInfo showOne(
            final Long id,
            final NotifyType notifyType
    ) {
        return notifyRepository.findDetail(id, notifyType);
    }

    public void write(
            final
    ){

    }
}
