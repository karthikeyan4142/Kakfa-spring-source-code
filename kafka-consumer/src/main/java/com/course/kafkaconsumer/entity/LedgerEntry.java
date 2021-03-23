package com.course.kafkaconsumer.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LedgerEntry {

	private double creditAmount;

	private String creditDescription;

	private double debitAmount;

	private String debitDescription;

	public LedgerEntry() {
	}

	public LedgerEntry(double creditAmount, String creditDescription, double debitAmount, String debitDescription) {
		super();
		this.creditAmount = creditAmount;
		this.creditDescription = creditDescription;
		this.debitAmount = debitAmount;
		this.debitDescription = debitDescription;
	}

	@Override
	public String toString() {
		return "LedgerEntry [creditAmount=" + creditAmount + ", creditDescription=" + creditDescription
				+ ", debitAmount=" + debitAmount + ", debitDescription=" + debitDescription + "]";
	}

}
