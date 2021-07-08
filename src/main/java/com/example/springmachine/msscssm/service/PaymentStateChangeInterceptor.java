package com.example.springmachine.msscssm.service;

import com.example.springmachine.msscssm.domain.Payment;
import com.example.springmachine.msscssm.domain.PaymentEvent;
import com.example.springmachine.msscssm.domain.PaymentState;
import com.example.springmachine.msscssm.repository.PaymentRepository;
import com.example.springmachine.msscssm.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository repository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message, Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine, StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
            .ifPresent(paymentId -> {
                Payment payment = repository.getById(paymentId);
                payment.setState(state.getId());
                repository.save(payment);
            });
        });
    }
}
