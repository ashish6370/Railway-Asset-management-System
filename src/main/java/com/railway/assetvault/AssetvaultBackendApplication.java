package com.railway.assetvault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@org.springframework.scheduling.annotation.EnableScheduling
@EnableJpaAuditing
public class AssetvaultBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssetvaultBackendApplication.class, args);
	}
}
