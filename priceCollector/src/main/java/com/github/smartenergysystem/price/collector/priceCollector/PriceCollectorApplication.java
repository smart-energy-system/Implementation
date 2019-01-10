package com.github.smartenergysystem.price.collector.priceCollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCollectorApplication.class, args);
	}

}

