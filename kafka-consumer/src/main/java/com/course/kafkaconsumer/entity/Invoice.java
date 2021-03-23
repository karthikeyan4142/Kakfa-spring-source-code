package com.course.kafkaconsumer.entity;

public class Invoice {

	private double amount;

	private String currency;

	private String number;

	public Invoice() {

	}

	public Invoice(double amount, String number, String currency) {
		super();
		this.amount = amount;
		this.number = number;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public String getNumber() {
		return number;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Invoice [amount=" + amount + ", number=" + number + ", currency=" + currency + "]";
	}

}
