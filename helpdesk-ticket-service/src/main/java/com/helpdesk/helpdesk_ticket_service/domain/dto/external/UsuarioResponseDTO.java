package com.helpdesk.helpdesk_ticket_service.domain.dto.external;

import lombok.Data;

// DTO que representa la respuesta del user-management-service.
// Se replica aquí para desacoplar los servicios.
@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombreCompleto;
    private String departamento;
}
