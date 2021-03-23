package com.course.kafkaconsumer.exception.handler;

import java.util.function.BiConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodOrderRecoverer implements BiConsumer<ConsumerRecord<?, ?>, Exception> {

	private static final Logger log = LoggerFactory.getLogger(FoodOrderRecoverer.class);

	@Override
	public void accept(ConsumerRecord<?, ?> record, Exception exception) {
		log.info("FoodOrderRecoverer accepting {} with exception {}", record.value(), exception);
	}

}
