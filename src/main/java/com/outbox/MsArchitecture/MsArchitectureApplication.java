package com.outbox.MsArchitecture;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.outbox.MsArchitecture.entity.User;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
 import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class MsArchitectureApplication {
	public static void main(String[] args) throws JsonProcessingException {
      SpringApplication.run(MsArchitectureApplication.class, args);
	}

}
