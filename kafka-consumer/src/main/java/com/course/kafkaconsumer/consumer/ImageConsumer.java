package com.course.kafkaconsumer.consumer;

import java.io.IOException;
import java.net.http.HttpConnectTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.course.kafkaconsumer.entity.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageConsumer {

	private static final Logger log = LoggerFactory.getLogger(ImageConsumer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@KafkaListener(topics = "t_images", containerFactory = "imageRetryContainerFactory")
	public void consume(String message) throws IOException {
		var image = objectMapper.readValue(message, Image.class);

		if (image.getType().equalsIgnoreCase("svg")) {
			throw new HttpConnectTimeoutException("Simulate API call failed");
		}

		log.info("Processing image : {}", image);
	}

}
