package com.argyle.fibonacci.controller;

import com.argyle.fibonacci.exception.Error;
import com.argyle.fibonacci.exception.ErrorCodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FibControllerTest {

    private static final String URL = "/fib";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Calculate fibonacci success scenario: OK")
    void calculateFibonacci_Success() {
        //GIVEN
        Map<Integer, BigInteger> expectedResult = new HashMap<>(){{
            put(1, BigInteger.ONE);
            put(10, BigInteger.valueOf(55));
            put(72, new BigInteger("498454011879264"));

        }};

        expectedResult.forEach((k,v ) -> {

            //WHEN
            var QUERY_PARAM_N = "?n=".concat(k.toString());
            ResponseEntity<String> response =
                    testRestTemplate.exchange(
                            URL.concat(QUERY_PARAM_N) , HttpMethod.GET, null, String.class);

            // THEN
            Assertions.assertEquals(v, new BigInteger(response.getBody()));
        });
    }

    @Test
    @DisplayName("Calculate fibonacci without parameter N: BAD_REQUEST")
    void calculateFibonacci_WithoutRequestParam() throws ExecutionException, InterruptedException {

        //GIVEN
        var expectedMessage = "n : Required request parameter 'n' for method parameter type int is not present";

        // WHEN
        ResponseEntity<Error> response =
                testRestTemplate.exchange(URL, HttpMethod.GET, null, Error.class);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode())
                .isEqualTo(ErrorCodes.MALFORMED_REQUEST.getCode());
        assertThat(response.getBody().getDetails().get(0).contains(expectedMessage)).isTrue();
    }

    @Test
    @DisplayName("Calculate fibonacci with incomplete parameter N: BAD_REQUEST")
    void calculateFibonacci_WithIncompleteParam() throws ExecutionException, InterruptedException {
        //GIVEN
        var expectedMessage = "NumberFormatException: For input string";

        // WHEN
        ResponseEntity<Error> response =
                testRestTemplate.exchange(URL.concat("?n="), HttpMethod.GET, null, Error.class);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode())
                .isEqualTo(ErrorCodes.MALFORMED_REQUEST.getCode());
        assertThat(response.getBody().getDetails().get(0).contains(expectedMessage)).isTrue();
    }

}