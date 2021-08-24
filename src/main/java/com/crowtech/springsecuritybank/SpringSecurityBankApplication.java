package com.crowtech.springsecuritybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({ @ComponentScan("com.crowtech.controllers"), @ComponentScan("com.crowtech.config")  })
public class SpringSecurityBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBankApplication.class, args);
	}

}
