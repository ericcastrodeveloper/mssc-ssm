package com.example.springmachine.msscssm.service.impl;

import com.example.springmachine.msscssm.domain.Payment;
import com.example.springmachine.msscssm.repository.PaymentRepository;
import com.example.springmachine.msscssm.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService service;

    @Autowired
    PaymentRepository repository;

    Payment payment;


    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = service.newPayment(payment);
        System.out.println(savedPayment.getState());

        var sm = service.preAuth(savedPayment.getId());

        Payment preAuthedPayment = repository.getById(payment.getId());

        System.out.println(sm.getState().getId());
        System.out.println(preAuthedPayment);
    }
}