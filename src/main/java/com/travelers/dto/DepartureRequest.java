package com.travelers.dto;

import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.departure.Departure;
import com.travelers.biz.domain.reservation.embeddable.Capacity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class DepartureRequest {

    @Getter
    @AllArgsConstructor
    public static class Create {
        private final Capacity capacity;
        private final LocalDate whenDeparture;

        public Departure toDeparture(final Product product) {
            return Departure.create(product, whenDeparture, capacity);
        }
    }
}
