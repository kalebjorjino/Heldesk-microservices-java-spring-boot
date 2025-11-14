package com.helpdesk.asset_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // Habilitar el registro en Eureka.
@EnableFeignClients
public class AssetManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementServiceApplication.class, args);
	}

}
