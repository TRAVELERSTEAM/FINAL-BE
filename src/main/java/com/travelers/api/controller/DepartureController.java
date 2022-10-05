package com.travelers.api.controller;

import com.travelers.biz.service.DepartureService;
import com.travelers.dto.DepartureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class DepartureController {

    private final DepartureService departureService;

    @GetMapping("/{productId}/departure")
    public ResponseEntity<List<DepartureResponse.TravelPeriod>> showList(
            @PathVariable final Long productId
    ) {
        return ResponseEntity.ok(departureService.availableReservationList(productId));
    }

    // TODO: 2022-10-06 경로 생각
    @GetMapping("/{productId}/departure/{departureId}")
    public ResponseEntity<DepartureResponse.ReservationInfo> showOne(
            @PathVariable final Long departureId
    ) {
        return ResponseEntity.ok(departureService.findDeparture(departureId));
    }

}
