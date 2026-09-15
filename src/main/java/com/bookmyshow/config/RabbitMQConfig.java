package com.bookmyshow.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // EXCHANGE

    public static final String EXCHANGE =
            "movie.booking.exchange";


    // =========================================================
    // SEAT HOLD QUEUE
    // =========================================================

    public static final String HOLD_QUEUE =
            "movie.booking.hold.expiry.queue";

    public static final String HOLD_ROUTING_KEY =
            "seat.hold.expiry";


    // =========================================================
    // EXPIRED SEAT QUEUE
    // =========================================================

    public static final String EXPIRED_QUEUE =
            "movie.booking.hold.expired.queue";

    public static final String EXPIRED_ROUTING_KEY =
            "seat.hold.expired";


    // =========================================================
    // BOOKING CONFIRMED QUEUE
    // =========================================================

    public static final String BOOKING_CONFIRMED_QUEUE =
            "movie.booking.confirmed.queue";

    public static final String BOOKING_CONFIRMED_ROUTING_KEY =
            "booking.confirmed";


    // =========================================================
    // EXCHANGE
    // =========================================================

    @Bean
    public DirectExchange bookingExchange() {

        return new DirectExchange(EXCHANGE);
    }


    // =========================================================
    // HOLD EXPIRY QUEUE
    // =========================================================

    @Bean
    public Queue holdExpiryQueue() {

        Map<String, Object> arguments =
                new HashMap<>();

        // 5 minutes
        arguments.put(
                "x-message-ttl",
                300000
        );

        // Expired message goes back to exchange
        arguments.put(
                "x-dead-letter-exchange",
                EXCHANGE
        );

        // Expired message routing key
        arguments.put(
                "x-dead-letter-routing-key",
                EXPIRED_ROUTING_KEY
        );

        return new Queue(
                HOLD_QUEUE,
                true,
                false,
                false,
                arguments
        );
    }


    // =========================================================
    // EXPIRED QUEUE
    // =========================================================

    @Bean
    public Queue expiredQueue() {

        return new Queue(
                EXPIRED_QUEUE
        );
    }


    // =========================================================
    // BOOKING CONFIRMED QUEUE
    // =========================================================

    @Bean
    public Queue bookingConfirmedQueue() {

        return new Queue(
                BOOKING_CONFIRMED_QUEUE
        );
    }


    // =========================================================
    // HOLD QUEUE BINDING
    // =========================================================

    @Bean
    public Binding holdExpiryBinding(
            Queue holdExpiryQueue,
            DirectExchange bookingExchange) {

        return BindingBuilder
                .bind(holdExpiryQueue)
                .to(bookingExchange)
                .with(HOLD_ROUTING_KEY);
    }


    // =========================================================
    // EXPIRED QUEUE BINDING
    // =========================================================

    @Bean
    public Binding expiredQueueBinding(
            Queue expiredQueue,
            DirectExchange bookingExchange) {

        return BindingBuilder
                .bind(expiredQueue)
                .to(bookingExchange)
                .with(EXPIRED_ROUTING_KEY);
    }


    // =========================================================
    // BOOKING CONFIRMED BINDING
    // =========================================================

    @Bean
    public Binding bookingConfirmedBinding(
            Queue bookingConfirmedQueue,
            DirectExchange bookingExchange) {

        return BindingBuilder
                .bind(bookingConfirmedQueue)
                .to(bookingExchange)
                .with(BOOKING_CONFIRMED_ROUTING_KEY);
    }


    // =========================================================
    // JSON MESSAGE CONVERTER
    // =========================================================

    @Bean
    public MessageConverter messageConverter() {

        return new JacksonJsonMessageConverter();
    }
}