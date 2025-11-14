package com.helpdesk.helpdesk_ticket_service.infrastructure.controller;

import com.helpdesk.helpdesk_ticket_service.application.ports.in.TicketUseCase;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketRequestDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketStateStatsDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.EquipoResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketUseCase ticketUseCase;

    public TicketController(TicketUseCase ticketUseCase) {
        this.ticketUseCase = ticketUseCase;
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> crearTicket(@RequestBody TicketRequestDTO requestDTO) {
        return new ResponseEntity<>(ticketUseCase.crearTicket(requestDTO), HttpStatus.CREATED);
    }



    @GetMapping("/my-tickets")
    public ResponseEntity<List<TicketResponseDTO>> obtenerMisTickets() {
        return ResponseEntity.ok(ticketUseCase.obtenerMisTickets());
    }

    @GetMapping("/assigned-to-me")
    public ResponseEntity<List<TicketResponseDTO>> obtenerTicketsAsignados() {
        return ResponseEntity.ok(ticketUseCase.obtenerTicketsAsignadosAMi());
    }

    @GetMapping("/equipos-por-usuario/{usuarioId}")
    public ResponseEntity<List<EquipoResponseDTO>> obtenerEquiposPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ticketUseCase.obtenerEquiposPorUsuarioReportaId(usuarioId));
    }

    @GetMapping("/estadisticas/por-estado")
    public ResponseEntity<List<TicketStateStatsDTO>> obtenerEstadisticasPorEstado() {
        return ResponseEntity.ok(ticketUseCase.obtenerEstadisticasPorEstado());
    }

    @GetMapping("/estadisticas/cerrados-24h")
    public ResponseEntity<Long> contarTicketsCerradosUltimas24h() {
        return ResponseEntity.ok(ticketUseCase.contarTicketsCerradosUltimas24h());
    }



    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> obtenerTickets(
            @RequestParam(required = false) EstadoTicket estado,
            @RequestParam(required = false) PrioridadTicket prioridad,
            @RequestParam(required = false) Long usuarioId) {

        List<TicketResponseDTO> tickets;
        if (estado != null) {
            tickets = ticketUseCase.obtenerTicketsPorEstado(estado);
        } else if (prioridad != null) {
            tickets = ticketUseCase.obtenerTicketsPorPrioridad(prioridad);
        } else if (usuarioId != null) {
            tickets = ticketUseCase.obtenerTicketsPorUsuario(usuarioId);
        } else {
            tickets = ticketUseCase.obtenerTodosLosTickets();
        }
        return ResponseEntity.ok(tickets);
    }



    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtenerTicketPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ticketUseCase.obtenerTicketPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizarTicket(@PathVariable Long id, @RequestBody TicketRequestDTO requestDTO) {
        return ResponseEntity.ok(ticketUseCase.actualizarTicket(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable Long id) {
        ticketUseCase.eliminarTicket(id);
        return ResponseEntity.noContent().build();
    }
}
