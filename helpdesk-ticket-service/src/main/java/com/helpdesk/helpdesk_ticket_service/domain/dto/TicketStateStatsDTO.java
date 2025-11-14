package com.helpdesk.helpdesk_ticket_service.domain.dto;

import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketStateStatsDTO {
    private EstadoTicket estado;
    private Long count;
}
