package com.helpdesk.helpdesk_ticket_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {

    @JsonProperty("asunto")
    private String asunto;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("prioridad")
    private PrioridadTicket prioridad;

    @JsonProperty("estado")
    private EstadoTicket estado;

    @JsonProperty("usuarioReportaId")
    private Long usuarioReportaId;

    @JsonProperty("equipoAfectadoId")
    private Long equipoAfectadoId;

    @JsonProperty("tecnicoAsignadoId")
    private Long tecnicoAsignadoId;

    @JsonProperty("componenteId") // <-- CAMPO AÑADIDO
    private Long componenteId;

    @JsonProperty("diagnostico")
    private String diagnostico;
}
