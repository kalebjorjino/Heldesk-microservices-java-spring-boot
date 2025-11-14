package com.helpdesk.helpdesk_ticket_service.domain.dto;

import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    // Datos del Ticket
    private Long id;
    private String asunto;
    private String descripcion;
    private EstadoTicket estado;
    private PrioridadTicket prioridad;
    private LocalDateTime fechaCreacion;
    private String diagnostico;

    // --- Datos Enriquecidos ---
    private Long usuarioReportaId;
    private String nombreUsuarioReporta;

    private Long equipoAfectadoId;
    private String detallesEquipo;

    private Long tecnicoAsignadoId;
    private String nombreTecnicoAsignado;

    private Long componenteId; // <-- CAMPO AÑADIDO
    private String detallesComponente; // <-- CAMPO AÑADIDO
}
