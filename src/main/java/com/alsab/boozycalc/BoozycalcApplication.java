package com.alsab.boozycalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
public class BoozycalcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoozycalcApplication.class, args);
	}

}
