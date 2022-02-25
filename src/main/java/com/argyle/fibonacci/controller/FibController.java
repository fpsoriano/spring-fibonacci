package com.argyle.fibonacci.controller;

import com.argyle.fibonacci.service.FibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@RequestMapping("/fib")
public class FibController {

    @Autowired
    FibService fibService;

    @GetMapping
    public BigInteger getFib(@Valid @RequestParam int n) {
        return fibService.calculateFibonacci(n);
    }
}
