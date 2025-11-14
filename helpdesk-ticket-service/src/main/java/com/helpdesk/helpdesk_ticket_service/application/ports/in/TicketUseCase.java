package com.helpdesk.helpdesk_ticket_service.application.ports.in;

import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketRequestDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketStateStatsDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.EquipoResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketUseCase {


    List<TicketStateStatsDTO> obtenerEstadisticasPorEstado();
    Long contarTicketsCerradosUltimas24h();


    List<TicketResponseDTO> obtenerMisTickets();
    List<TicketResponseDTO> obtenerTicketsAsignadosAMi();


    List<TicketResponseDTO> obtenerTicketsCerrados();
    List<TicketResponseDTO> obtenerTicketsPendientesParaDashboard();

    List<TicketResponseDTO> obtenerTicketsCerradosDesde(LocalDateTime fechaDesde);


    TicketResponseDTO crearTicket(TicketRequestDTO requestDTO);
    TicketResponseDTO obtenerTicketPorId(Long id);
    List<TicketResponseDTO> obtenerTodosLosTickets();
    TicketResponseDTO actualizarTicket(Long id, TicketRequestDTO requestDTO);
    void eliminarTicket(Long id);


    List<TicketResponseDTO> obtenerTicketsPorEstado(EstadoTicket estado);
    List<TicketResponseDTO> obtenerTicketsPorPrioridad(PrioridadTicket prioridad);
    List<TicketResponseDTO> obtenerTicketsPorUsuario(Long usuarioId);

    List<EquipoResponseDTO> obtenerEquiposPorUsuarioReportaId(Long usuarioReportaId);
}

