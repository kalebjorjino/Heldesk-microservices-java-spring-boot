package com.helpdesk.helpdesk_ticket_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // Habilitar el registro en Eureka.
@EnableFeignClients // Habilita la comunicación inter-servicios
public class HelpdeskTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskTicketServiceApplication.class, args);
	}

}
