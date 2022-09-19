package com.travelers.jwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Base64;

@Slf4j

public class Base64Test {

    private String input = "spring-boot-jwt-test-vazirakcarlcox-ojinghumuchim-icecream-boricha-cocoa-bob";
    private byte[] inputByte = input.getBytes();
    @Test
    void Base64EncodingDecoding() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(inputByte);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(encoded);

        log.info(new String(encoded));
        log.info(new String(decoded));
    }
}
