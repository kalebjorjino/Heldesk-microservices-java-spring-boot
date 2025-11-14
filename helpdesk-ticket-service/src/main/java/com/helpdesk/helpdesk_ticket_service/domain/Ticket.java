package com.helpdesk.helpdesk_ticket_service.domain;

import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String asunto;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoTicket estado = EstadoTicket.PENDIENTE;

    @Enumerated(EnumType.STRING)
    private PrioridadTicket prioridad;

    private Long usuarioReportaId;
    private Long equipoAfectadoId;
    private Long tecnicoAsignadoId;
    private Long componenteId;

    @Column(updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private String diagnostico;
    private LocalDateTime fechaCierre;
    private LocalDateTime fechaActualizacion;
}
