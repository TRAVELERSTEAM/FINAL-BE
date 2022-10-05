package com.travelers.biz.service;

import com.travelers.biz.repository.departure.DepartureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartureService {

    private final DepartureRepository departureRepository;
    
}
