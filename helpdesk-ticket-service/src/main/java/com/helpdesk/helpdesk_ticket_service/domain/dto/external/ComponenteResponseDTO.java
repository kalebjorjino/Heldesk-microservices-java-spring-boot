package com.helpdesk.helpdesk_ticket_service.domain.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Este DTO representa la respuesta que esperamos del Device-Management-Service
// al pedir los detalles de un componente.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponenteResponseDTO {
    private Long id;
    private String nombre;

}
