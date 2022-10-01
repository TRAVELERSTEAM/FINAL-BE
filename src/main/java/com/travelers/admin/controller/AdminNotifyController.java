package com.travelers.admin.controller;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.service.NotifyService;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.PagingRequest;
import com.travelers.dto.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class AdminNotifyController {

    private final NotifyService notifyService;

    @GetMapping("/notice")
    public ResponseEntity<PagingResponse<NotifyResponse.SimpleInfo>> findAll(
            final PagingRequest.Info pagingInfo
    ) {
        return ResponseEntity.ok(
                notifyService.showNotifies(NotifyType.NOTICE, pagingInfo)
        );
    }

    @GetMapping("/notice/{noticeId}")
    public ResponseEntity<NotifyResponse.DetailInfo> init(
            @PathVariable final Long noticeId
    ) {
        return ResponseEntity.ok(
                notifyService.showOne(noticeId, NotifyType.NOTICE)
        );
    }

}
