package com.travelers.exception;

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalHandler {

    public static <T> T findMember(Supplier<Optional<T>> supplier){
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public static <T> T findWithNotfound(Supplier<Optional<T>> supplier){
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public static <T> T findProduct(Supplier<Optional<T>> supplier){
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public static <T> T findReview(Supplier<Optional<T>> supplier) {
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
