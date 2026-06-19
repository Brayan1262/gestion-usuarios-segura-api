package com.brayan.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureUserManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureUserManagementApiApplication.class, args);
		System.out.println("🚀 Secure User Management API Started Successfully!");
	}

}
