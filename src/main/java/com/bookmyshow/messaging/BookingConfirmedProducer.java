package com.bookmyshow.messaging;

import com.bookmyshow.config.RabbitMQConfig;
import com.bookmyshow.dto.BookingConfirmedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingConfirmedProducer {

    private final RabbitTemplate rabbitTemplate;


    public BookingConfirmedProducer(
            RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendBookingConfirmedMessage(
            BookingConfirmedMessage message) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.BOOKING_CONFIRMED_ROUTING_KEY,
                message
        );
    }
}