package com.travelers.api.controller;

import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.service.NotifyService;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotifyController {

    private final NotifyService notifyService;

    @GetMapping("/notice")
    public ResponseEntity<PagingCorrespondence.Response<NotifyResponse.SimpleInfo>> findNoticeAll(
            final PagingCorrespondence.Request pagingInfo
    ) {
        return ResponseEntity.ok(
                notifyService.showNotifies(NotifyType.NOTICE, pagingInfo)
        );
    }

    @GetMapping("/ref-library")
    public ResponseEntity<PagingCorrespondence.Response<NotifyResponse.SimpleInfo>> findRefLibraryAll(
            final PagingCorrespondence.Request pagingInfo
    ) {
        return ResponseEntity.ok(
                notifyService.showNotifies(NotifyType.REFERENCE_LIBRARY, pagingInfo)
        );
    }

    @GetMapping("/notice/{notifyId}")
    public ResponseEntity<NotifyResponse.DetailInfo> findNotice(
            @PathVariable final Long notifyId
    ) {
        return ResponseEntity.ok(
                notifyService.showOne(notifyId, NotifyType.NOTICE)
        );
    }

    @GetMapping("/ref-library/{notifyId}")
    public ResponseEntity<NotifyResponse.DetailInfo> findRefLibrary(
            @PathVariable final Long notifyId
    ){
        return ResponseEntity.ok(
                notifyService.showOne(notifyId, NotifyType.REFERENCE_LIBRARY)
        );
    }
}
