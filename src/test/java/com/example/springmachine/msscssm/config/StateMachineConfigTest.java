package com.example.springmachine.msscssm.config;

import com.example.springmachine.msscssm.domain.PaymentEvent;
import com.example.springmachine.msscssm.domain.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<PaymentState, PaymentEvent> factory;

    @Test
    public void testNewStateMachine(){
        StateMachine<PaymentState, PaymentEvent> sm = factory.getStateMachine(UUID.randomUUID());
        sm.startReactively().subscribe();

        Message<PaymentEvent> message = MessageBuilder.withPayload(PaymentEvent.PRE_AUTHORIZE).build();
        sm.sendEvent(Mono.just(message)).subscribe();

        message = MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_APPROVED).build();
        sm.sendEvent(Mono.just(message)).subscribe();

    }
}