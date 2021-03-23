package com.course.kafkaconsumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;

//@Service
public class HelloKafkaConsumer {

	@KafkaListener(topics = "t_hello")
	public void consume(String message) {
		System.out.println(message);
	}

}
