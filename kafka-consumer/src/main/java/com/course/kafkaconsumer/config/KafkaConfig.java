package com.course.kafkaconsumer.config;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.course.kafkaconsumer.entity.CarLocation;
import com.course.kafkaconsumer.exception.handler.GlobalErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class KafkaConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		var properties = kafkaProperties.buildConsumerProperties();
		properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "600000");

		return new DefaultKafkaConsumerFactory<>(properties);
	}

	@Bean(name = "farLocationContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

			private ObjectMapper objectMapper = new ObjectMapper();

			@Override
			public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
				try {
					var carLocation = objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);
					return carLocation.getDistance() <= 100;
				} catch (IOException e) {
					return false;
				}
			}
		});

		return factory;
	}

	@Bean(value = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		factory.setErrorHandler(new GlobalErrorHandler());

		return factory;
	}

	@Bean(value = "imageRetryContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());

		factory.setErrorHandler(new GlobalErrorHandler());
		factory.setRetryTemplate(createRetryTemplate());
		factory.setRecoveryCallback(new RecoveryCallback<Object>() {

			@Override
			public Object recover(RetryContext context) throws Exception {
				System.out.println("Recovery callback " + context.getRetryCount());
				return null;
			}
		});

		return factory;
	}

	@Bean
	public RetryTemplate createRetryTemplate() {
		var retryTemplate = new RetryTemplate();

		var retryPolicy = new SimpleRetryPolicy(3);
		retryTemplate.setRetryPolicy(retryPolicy);

		var backoffPolicy = new FixedBackOffPolicy();
		backoffPolicy.setBackOffPeriod(10000);
		retryTemplate.setBackOffPolicy(backoffPolicy);

		return retryTemplate;
	}

	@Bean(value = "invoiceDltContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> invoiceDltContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer, KafkaTemplate<Object, Object> template) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		
		var recoverer = new DeadLetterPublishingRecoverer(template,
				(record, ex) -> new TopicPartition("t_invoice_dlt", record.partition()));

		factory.getContainerProperties().setAckOnError(false);

		var errorHandler = new SeekToCurrentErrorHandler(recoverer, 5);
		factory.setErrorHandler(errorHandler);

		return factory;
	}

}
