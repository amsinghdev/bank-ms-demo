package com.amit.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.amit.loans.repository")
@EntityScan("com.amit.loans.model")
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
