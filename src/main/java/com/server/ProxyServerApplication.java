package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.server")
@SpringBootApplication
public class ProxyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyServerApplication.class, args);
	}

}
