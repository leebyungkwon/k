package com.skm.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SkmSsoApplication extends SpringBootServletInitializer {
	 
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SkmSsoApplication.class);
	}
	
	public static void main(String[] args) {
		System.out.println("###### sso service starting ####");
		SpringApplication.run(SkmSsoApplication.class, args);
	}

}
