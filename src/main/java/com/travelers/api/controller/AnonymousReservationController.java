package com.travelers.api.controller;

import com.travelers.biz.service.ReservationService;
import com.travelers.dto.AnonymousReservationResInfo;
import com.travelers.dto.ReservationRequest;
import com.travelers.dto.ReservationResInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/anonymous")
public class AnonymousReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{reservationCode}")
    public ResponseEntity<AnonymousReservationResInfo> anonymousReservation(
            @PathVariable final String reservationCode
    ) {
        return ResponseEntity.ok(reservationService.findAnonymousReservation(reservationCode));
    }

    @PostMapping("/{departureId}")
    public ResponseEntity<Void> createNonMember(
            @PathVariable final Long departureId,
            @RequestBody final ReservationRequest.NonMember headCount
    ) {
        reservationService.nonMemberCreate(departureId, headCount);
        return ResponseEntity.ok().build();
    }

}
