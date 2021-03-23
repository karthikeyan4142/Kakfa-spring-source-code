package com.course.kafkaproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarLocation {

	@JsonProperty("car_id")
	private String carId;

	private int distance;

	private long timestamp;

	public CarLocation(String carId, int distance, long timestamp) {
		super();
		this.carId = carId;
		this.distance = distance;
		this.timestamp = timestamp;
	}

	public String getCarId() {
		return carId;
	}

	public int getDistance() {
		return distance;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "CarLocation [carId=" + carId + ", distance=" + distance + ", timestamp=" + timestamp + "]";
	}

}
