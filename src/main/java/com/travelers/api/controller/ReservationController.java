package com.travelers.api.controller;

import com.travelers.biz.domain.reservation.embeddable.HeadCount;
import com.travelers.biz.service.ReservationService;
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
    public void temp() {

    }

    @PostMapping
    public ResponseEntity<Void> temp1(
            final Long departureId,
            @RequestBody final HeadCount headCount
    ) {
        reservationService.create(currentMemberId(), departureId, headCount);
        return ResponseEntity.noContent().build();
    }

    private Long currentMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }
}
