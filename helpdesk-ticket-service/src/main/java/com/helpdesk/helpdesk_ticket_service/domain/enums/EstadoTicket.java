package com.helpdesk.helpdesk_ticket_service.domain.enums;

public enum EstadoTicket {
    PENDIENTE,    // El ticket ha sido creado pero nadie lo ha tomado aún.
    EN_PROCESO,   // Un técnico está trabajando en el ticket.
    RESUELTO,     // El técnico ha solucionado el problema, pendiente de confirmación del usuario.
    CERRADO,      // El problema ha sido resuelto y confirmado.
    RECHAZADO     // El ticket no procede o es inválido.
}
