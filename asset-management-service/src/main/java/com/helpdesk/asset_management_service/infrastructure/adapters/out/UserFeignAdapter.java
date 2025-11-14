package com.helpdesk.asset_management_service.infrastructure.adapters.out;

import com.helpdesk.asset_management_service.application.ports.out.UserPort;
import com.helpdesk.asset_management_service.domain.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component; // 👈 NECESARIO
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// La interfaz técnica de Feign para la comunicación HTTP.
@FeignClient(name = "USER-MANAGEMENT-SERVICE")
interface InternalUserFeignClient {
    @GetMapping("/api/usuarios/{id}")
    UsuarioDto getUsuarioById(@PathVariable("id") Long id);
}


@Component
public class UserFeignAdapter implements UserPort {

    private final InternalUserFeignClient feignClient;

    public UserFeignAdapter(InternalUserFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public UsuarioDto fetchUsuarioById(Long id) {
        if (id == null) {
            return null;
        }

        try {
            return feignClient.getUsuarioById(id);
        } catch (Exception e) {
            // Manejo de errores de infraestructura
            System.err.println("Error al contactar al servicio de usuarios: " + e.getMessage());
            throw new RuntimeException("Servicio de usuarios no disponible o ID no encontrado.", e);
        }
    }
}
