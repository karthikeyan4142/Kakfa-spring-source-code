package com.course.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.course.kafkaproducer.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SimpleNumberProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void send(SimpleNumber simpleNumber) {
		try {
			var json = objectMapper.writeValueAsString(simpleNumber);
			kafkaTemplate.send("t_simple_number", json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
