package com.helpdesk.helpdesk_ticket_service.domain.dto.external;

import lombok.Data;

// DTO que representa la respuesta del asset-management-service.
@Data
public class EquipoResponseDTO {
    private Long id;
    private String codigoPatrimonial;
    private String marca;
    private String modelo;
}
