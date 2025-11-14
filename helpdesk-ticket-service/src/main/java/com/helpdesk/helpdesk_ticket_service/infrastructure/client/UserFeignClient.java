package com.helpdesk.helpdesk_ticket_service.infrastructure.client;

import com.helpdesk.helpdesk_ticket_service.domain.dto.external.UserProfileDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.UsuarioResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.enums.Rol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "USER-MANAGEMENT-SERVICE")
public interface UserFeignClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioResponseDTO obtenerUsuarioPorId(@PathVariable("id") Long id);


    @GetMapping("/api/auth/profile")
    UserProfileDTO obtenerMiPerfil();


    @GetMapping("/api/usuarios")
    List<UsuarioResponseDTO> obtenerUsuariosPorRol(@RequestParam("rol") Rol rol);
}
