package com.travelers.api.controller;

import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import com.travelers.biz.service.ReservationService;
import com.travelers.dto.ReservationRequest;
import com.travelers.dto.ReservationResInfo;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<PagingCorrespondence.Response<ReservationResInfo>> showList(
            final PagingCorrespondence.Request pagingInfo
    ) {
        return ResponseEntity.ok(
                reservationService.reservationList(currentMemberId(), pagingInfo)
        );
    }

    @PostMapping("/{departureId}")
    public ResponseEntity<Void> create(
            @PathVariable final Long departureId,
            @RequestBody final HeadCount headCount
    ) {
        reservationService.memberCreate(currentMemberId(), departureId, headCount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{departureId}/anonymous")
    public ResponseEntity<Void> createNonMember(
            @PathVariable final Long departureId,
            @RequestBody final ReservationRequest.Anonymous headCount
    ) {
        reservationService.nonMemberCreate(departureId, headCount);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<Void> cancel(
            @PathVariable final Long reservationId
    ) {
        reservationService.cancel(currentMemberId(), reservationId);
        return ResponseEntity.ok().build();
    }

    private Long currentMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }
}
