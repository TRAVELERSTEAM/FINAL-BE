package com.travelers.exception;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class OptionalHandler {

    @Deprecated(since = "recommend : Functional 타입을 쓰는 메서드로 변경 권장")
    public static <T> T findMember(Supplier<Optional<T>> supplier) {
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Deprecated(since = "recommend : Functional 타입을 쓰는 메서드로 변경 권장")
    public static <T> T findWithNotfound(Supplier<Optional<T>> supplier) {
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Deprecated(since = "recommend : Functional 타입을 쓰는 메서드로 변경 권장")
    public static <T> T findProduct(Supplier<Optional<T>> supplier) {
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Deprecated(since = "recommend : Functional 타입을 쓰는 메서드로 변경 권장")
    public static <T> T findReview(Supplier<Optional<T>> supplier) {
        return supplier.get()
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public static <T, R> R findMemberOrThrow(T identifier, Function<T, Optional<R>> function) {
        return findOrThrow(identifier, function, ErrorCode.MEMBER_NOT_FOUND);
    }

    public static <T, R> R findProductOrThrow(T identifier, Function<T, Optional<R>> function) {
        return findOrThrow(identifier, function, ErrorCode.PRODUCT_NOT_FOUND);
    }

    public static <T, R> R findReviewOrThrow(T identifier, Function<T, Optional<R>> function){
        return findOrThrow(identifier, function, ErrorCode.RESOURCE_NOT_FOUND);
    }

    public static <T, R> R findDepartureOrThrow(T identifier, Function<T, Optional<R>> function){
        return findOrThrow(identifier, function, ErrorCode.RESOURCE_NOT_FOUND);
    }
    
    public static <T, R> R findNotifyOrThrow(T identifier, Function<T, Optional<R>> function){
        return findOrThrow(identifier, function, ErrorCode.RESOURCE_NOT_FOUND);
    }

    public static <T, R> R findOrThrow(T identifier, Function<T, Optional<R>> function, ErrorCode errorCode) {
        return orElseThrow(() -> function.apply(identifier), errorCode);
    }

    public static <T, R> R findOrThrow(final T identifier1, final T identifier2, BiFunction<T, T, Optional<R>> biFunction, ErrorCode errorCode) {
        return orElseThrow(() -> biFunction.apply(identifier1, identifier2), errorCode);
    }

    public static <T, U, R> R findBiOrThrow(final T identifier1, final U identifier2, BiFunction<T, U, Optional<R>> biFunction, ErrorCode errorCode) {
        return orElseThrow(() -> biFunction.apply(identifier1, identifier2), errorCode);
    }

    private static <R> R orElseThrow(Supplier<Optional<R>> supplier, ErrorCode errorCode) {
        return supplier.get()
                .orElseThrow(() -> new TravelersException(errorCode));
    }
}
