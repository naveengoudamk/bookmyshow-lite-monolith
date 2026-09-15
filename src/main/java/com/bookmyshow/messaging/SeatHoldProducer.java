package com.bookmyshow.messaging;

import com.bookmyshow.config.RabbitMQConfig;
import com.bookmyshow.dto.SeatHoldMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeatHoldProducer {

    private final RabbitTemplate rabbitTemplate;


    public SeatHoldProducer(
            RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendHoldExpiryMessage(
            SeatHoldMessage message) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.HOLD_ROUTING_KEY,
                message
        );
    }
}