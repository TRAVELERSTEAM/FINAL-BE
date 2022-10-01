package com.travelers.admin.controller;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.service.NotifyService;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.dto.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class AdminNotifyController {

    private final NotifyService notifyService;

    @GetMapping("/notice")
    public ResponseEntity<PagingCorrespondence.Response<NotifyResponse.SimpleInfo>> findAll(
            final PagingCorrespondence.Request pagingInfo
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

    @PostMapping("/notice")
    public ResponseEntity<Void> create(
            final Long memberId,
            final Long
    ){
//        SecurityUtil.getCurrentMemberId();
    }
}
