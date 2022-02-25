package com.argyle.fibonacci.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class FibService {

    @Cacheable(cacheNames = {"fibonacci"}, key = "#position")
    public BigInteger calculateFibonacci(int position) {

        BigInteger a = BigInteger.ZERO, b = BigInteger.ONE, c;

        if (position == 0)
            return a;
        for (int i = 2; i <= position; i++)
        {
            c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }
}
