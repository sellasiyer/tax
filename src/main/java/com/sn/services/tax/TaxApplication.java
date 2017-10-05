package com.sn.services.tax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TaxApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TaxApplication.class, args);
		TaxClient taxClient = ctx.getBean(TaxClient.class);
		taxClient.getTaxQuotation();
	}
}
