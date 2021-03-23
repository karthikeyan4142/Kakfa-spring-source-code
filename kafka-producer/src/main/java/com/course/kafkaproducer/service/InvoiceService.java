package com.course.kafkaproducer.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.course.kafkaproducer.entity.Invoice;

@Service
public class InvoiceService {

	private static int counter = 0;
	
	public Invoice generateInvoice() {
		counter++;
		var invoiceNumber = "INV-" + counter;
		var amount = ThreadLocalRandom.current().nextLong(1, 1000);

		return new Invoice(amount, invoiceNumber, "USD");
	}
	
}
