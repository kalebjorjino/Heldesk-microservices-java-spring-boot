package com.helpdesk.helpdesk_ticket_service.domain.enums;

public enum PrioridadTicket {
    BAJA,         // Problema no urgente, puede ser atendido cuando haya tiempo.
    MEDIA,        // Problema estándar que debe ser atendido en un tiempo razonable.
    ALTA,         // Problema importante que afecta la productividad.
    CRITICA       // Problema bloqueante que requiere atención inmediata.
}
