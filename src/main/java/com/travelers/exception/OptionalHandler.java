package com.travelers.exception;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
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

    public static <T> T findOrThrow(Long id, Function<Long, Optional<T>> function, ErrorCode errorCode){
        return function.apply(id)
                .orElseThrow(() -> new TravelersException(errorCode));
    }

    public static <T> T findOrThrow(final Long id1, final Long id2, BiFunction<Long, Long, Optional<T>> function, ErrorCode errorCode){
        return function.apply(id1, id2)
                .orElseThrow(() -> new TravelersException(errorCode));
    }
}
