package org.bgi.springboot1;

import org.bgi.springboot1.api.tokens.UserTokenService;
import org.bgi.springboot1.api.tokens.UserTokenServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
	
	@Bean
	public UserTokenService userTokenService(){
		return new UserTokenServiceImpl();
	}

}
