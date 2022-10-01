package com.travelers.biz.service;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.repository.NotifyRepository;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.PagingRequest;
import com.travelers.dto.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyRepository notifyRepository;

    public PagingResponse<NotifyResponse.SimpleInfo> showNotifies(final NotifyType notifyType, final PagingRequest.Info pagingInfo){
        return notifyRepository.findSimpleList(notifyType, pagingInfo.toPageable());
    }

    public NotifyResponse.DetailInfo showOne(final Long id, final NotifyType notifyType) {
        return notifyRepository.findDetail(id, notifyType);
    }
}
