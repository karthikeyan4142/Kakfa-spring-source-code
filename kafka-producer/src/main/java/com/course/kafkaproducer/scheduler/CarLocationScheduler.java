package com.course.kafkaproducer.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.course.kafkaproducer.entity.CarLocation;
import com.course.kafkaproducer.producer.CarLocationProducer;

@Service
public class CarLocationScheduler {

	private static final Logger log = LoggerFactory.getLogger(CarLocationScheduler.class);

	private CarLocation carOne;

	private CarLocation carThree;

	private CarLocation carTwo;

	@Autowired
	private CarLocationProducer producer;

	public CarLocationScheduler() {
		var now = System.currentTimeMillis();

		carOne = new CarLocation("car-one", 0, now);
		carTwo = new CarLocation("car-two", 110, now);
		carThree = new CarLocation("car-three", 95, now);
	}

	@Scheduled(fixedRate = 5000)
	public void generateCarLocation() {
		var now = System.currentTimeMillis();
		carOne.setTimestamp(now);
		carTwo.setTimestamp(now);
		carThree.setTimestamp(now);

		carOne.setDistance(carOne.getDistance() + 1);
		carTwo.setDistance(carTwo.getDistance() - 1);
		carThree.setDistance(carThree.getDistance() + 1);

		producer.send(carOne);
		producer.send(carTwo);
		producer.send(carThree);

		log.info("Sent : {}", carOne);
		log.info("Sent : {}", carTwo);
		log.info("Sent : {}", carThree);
	}

}
