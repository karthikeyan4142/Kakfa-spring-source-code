package com.course.kafkaconsumer.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.course.kafkaconsumer.entity.Invoice;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InvoiceConsumer {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(InvoiceConsumer.class);

	@KafkaListener(topics = "t_invoice", containerFactory = "invoiceDltContainerFactory")
	public void consume(String message) throws IOException {
		var invoice = objectMapper.readValue(message, Invoice.class);

		if (invoice.getAmount() < 1) {
			throw new IllegalArgumentException("Invalid amount : " + invoice.getAmount());
		}

		log.info("Processing invoice : {}", invoice);
	}

}
