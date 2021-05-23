package com.tesinas.spring.jwt.mongodb;

import javax.annotation.Resource;

import com.tesinas.spring.jwt.mongodb.services.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSecurityJwtMongodbApplication implements CommandLineRunner {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtMongodbApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {

	}
}
