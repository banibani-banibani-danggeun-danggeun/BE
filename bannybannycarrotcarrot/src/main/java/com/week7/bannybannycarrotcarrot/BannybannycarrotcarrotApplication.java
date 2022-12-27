package com.week7.bannybannycarrotcarrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class BannybannycarrotcarrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BannybannycarrotcarrotApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {

		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}




}
