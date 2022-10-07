package com.travelers.admin.controller;

import com.travelers.biz.service.DepartureService;
import com.travelers.dto.DepartureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/departure")
public class AdminDepartureController {

    private final DepartureService departureService;

    @PostMapping
    public ResponseEntity<Void> create(
            final Long productId,
            final DepartureRequest.Create createReq
    ) {
        departureService.createDeparture(productId, createReq);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{departureId}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long departureId
    ) {
        departureService.delete(departureId);
        return ResponseEntity.noContent().build();
    }
}
