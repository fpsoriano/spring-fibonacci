package com.argyle.fibonacci.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class FibServiceTest {

    @InjectMocks
    FibService fibService;

    // I could use @ParameterizedTest, but does not support Map
    @Test
    void calculateFibonacci() {

        // GIVEN
        Map<Integer, BigInteger> expectedResult = new HashMap<>(){{
            put(1, BigInteger.ONE);
            put(10, BigInteger.valueOf(55));
            put(72, new BigInteger("498454011879264"));

        }};

        // WHEN - THEN
        expectedResult.forEach((k,v ) -> {
            Assertions.assertEquals(v, fibService.calculateFibonacci(k));
        });
    }

}