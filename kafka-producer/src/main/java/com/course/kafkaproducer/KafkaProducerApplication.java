package com.course.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.course.kafkaproducer.producer.InvoiceProducer;
import com.course.kafkaproducer.service.InvoiceService;

@SpringBootApplication
//@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceProducer invoiceProducer;

	@Override
	public void run(String... args) throws Exception {
//		var invoice1 = invoiceService.generateInvoice();
//		var invoice2 = invoiceService.generateInvoice();
//		var invoice3 = invoiceService.generateInvoice();
//		
//		invoice2.setAmount(-1);
//
//		invoiceProducer.send(invoice1);
//		invoiceProducer.send(invoice2);
//		invoiceProducer.send(invoice3);
		
		for (int i = 1; i <= 10; i++) {
			var invoice = invoiceService.generateInvoice();

			if (i > 5) {
				invoice.setAmount(0);
			}
			
			invoiceProducer.send(invoice);
		}
	}

}
