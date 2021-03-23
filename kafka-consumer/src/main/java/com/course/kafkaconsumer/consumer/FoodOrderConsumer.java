package com.course.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.course.kafkaconsumer.entity.FoodOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FoodOrderConsumer {

	private static final Logger log = LoggerFactory.getLogger(FoodOrderConsumer.class);

	private static final int MAX_AMOUNT_ORDER = 7;

	private ObjectMapper objectMapper = new ObjectMapper();

//	@KafkaListener(topics = "t_food_order")
	@KafkaListener(topics = "t_food_order", errorHandler = "myFoodOrderErrorHandler")
	public void consume(String message) throws IOException {
		var foodOrder = objectMapper.readValue(message, FoodOrder.class);

		if (foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
			throw new IllegalArgumentException("Food amount is too many");
		}

		log.info("Food order is {}", foodOrder);
	}

}
