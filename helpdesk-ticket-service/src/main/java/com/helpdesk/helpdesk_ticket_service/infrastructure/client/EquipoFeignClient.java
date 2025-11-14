package com.helpdesk.helpdesk_ticket_service.infrastructure.client;

import com.helpdesk.helpdesk_ticket_service.domain.dto.external.EquipoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "ASSET-MANAGEMENT-SERVICE")
public interface EquipoFeignClient {

    @GetMapping("/equipos/{id}")
    EquipoResponseDTO obtenerEquipoPorId(@PathVariable("id") Long id);

    @GetMapping("/equipos?usuarioId={usuarioId}")
    List<EquipoResponseDTO> obtenerEquiposPorUsuario(@PathVariable("usuarioId") Long usuarioId);

}
