package com.travelers.biz.service;

import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.biz.repository.departure.DepartureRepository;
import com.travelers.dto.DepartureRequest;
import com.travelers.dto.DepartureResponse;
import com.travelers.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.travelers.exception.OptionalHandler.findOrThrow;
import static com.travelers.exception.OptionalHandler.findWithNotfound;

@Service
@RequiredArgsConstructor
public class DepartureService {

    private final DepartureRepository departureRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<DepartureResponse.TravelPeriod> availableReservationList(final Long productId) {
        return departureRepository.availableReservationListBy(productId);
    }

    @Transactional(readOnly = true)
    public DepartureResponse.ReservationInfo findDeparture(final Long departureId) {
        return findWithNotfound(() -> departureRepository.findForReservation(departureId));
    }

    @Transactional
    public void createDeparture(
            final Long productId,
            final DepartureRequest.Create createReq
    ) {
        final Product product = findOrThrow(productId, productRepository::findById, ErrorCode.PRODUCT_NOT_FOUND);
        departureRepository.save(createReq.toDeparture(product));
    }

    @Transactional
    public void delete(
            final Long departureId
    ) {
        departureRepository.deleteById(departureId);
    }
}
