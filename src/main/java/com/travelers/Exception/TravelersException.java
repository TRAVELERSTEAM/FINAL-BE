package com.travelers.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TravelersException extends RuntimeException{
    private final ErrorCode errorCode;
}
