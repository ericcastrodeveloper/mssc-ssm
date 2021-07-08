package com.example.springmachine.msscssm.service;

import com.example.springmachine.msscssm.domain.Payment;
import com.example.springmachine.msscssm.domain.PaymentEvent;
import com.example.springmachine.msscssm.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

    Payment newPayment(Payment payment);
    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);
    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);

}
