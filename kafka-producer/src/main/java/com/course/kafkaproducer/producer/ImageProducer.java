package com.course.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.course.kafkaproducer.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ImageProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void send(Image image) {
		try {
			var json = objectMapper.writeValueAsString(image);
			kafkaTemplate.send("t_images", image.getType(), json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
