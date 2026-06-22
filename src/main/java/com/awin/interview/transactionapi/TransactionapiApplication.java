package com.awin.interview.transactionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TransactionapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionapiApplication.class, args);
	}

}
