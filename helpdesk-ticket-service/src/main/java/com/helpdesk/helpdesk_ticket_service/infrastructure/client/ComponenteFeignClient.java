package com.helpdesk.helpdesk_ticket_service.infrastructure.client;

import com.helpdesk.helpdesk_ticket_service.domain.dto.external.ComponenteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "DEVICE-MANAGEMENT-SERVICE")
public interface ComponenteFeignClient {


    @GetMapping("/api/componentes/{id}")
    ComponenteResponseDTO obtenerComponentePorId(@PathVariable("id") Long id);

}
