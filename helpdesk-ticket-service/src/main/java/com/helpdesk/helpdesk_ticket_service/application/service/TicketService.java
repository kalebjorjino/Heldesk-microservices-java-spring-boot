package com.helpdesk.helpdesk_ticket_service.application.service;

import com.helpdesk.helpdesk_ticket_service.application.exceptions.BusinessException;
import com.helpdesk.helpdesk_ticket_service.application.exceptions.ResourceNotFoundException;
import com.helpdesk.helpdesk_ticket_service.application.mappers.TicketMapper;
import com.helpdesk.helpdesk_ticket_service.application.ports.in.TicketUseCase;
import com.helpdesk.helpdesk_ticket_service.application.ports.out.TicketRepository;
import com.helpdesk.helpdesk_ticket_service.domain.Ticket;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketRequestDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketStateStatsDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.ComponenteResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.EquipoResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.UserProfileDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.UsuarioResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.Rol;
import com.helpdesk.helpdesk_ticket_service.infrastructure.client.ComponenteFeignClient;
import com.helpdesk.helpdesk_ticket_service.infrastructure.client.EquipoFeignClient;
import com.helpdesk.helpdesk_ticket_service.infrastructure.client.UserFeignClient;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TicketService implements TicketUseCase {


    @Override
    public List<TicketStateStatsDTO> obtenerEstadisticasPorEstado() {
        return ticketRepository.countTicketsByEstado();
    }

    @Override
    public Long contarTicketsCerradosUltimas24h() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
        return ticketRepository.countByEstadoAndFechaCierreAfter(EstadoTicket.CERRADO, twentyFourHoursAgo);
    }

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserFeignClient userFeignClient;
    private final EquipoFeignClient equipoFeignClient;
    private final ComponenteFeignClient componenteFeignClient;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, UserFeignClient userFeignClient, EquipoFeignClient equipoFeignClient, ComponenteFeignClient componenteFeignClient) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.userFeignClient = userFeignClient;
        this.equipoFeignClient = equipoFeignClient;
        this.componenteFeignClient = componenteFeignClient;
    }

    @Override
    public List<TicketResponseDTO> obtenerMisTickets() {
        UserProfileDTO usuarioActual = userFeignClient.obtenerMiPerfil();
        List<Ticket> tickets = ticketRepository.findByUsuarioReportaId(usuarioActual.getId());
        return ticketMapper.toResponseDTOList(tickets);
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsAsignadosAMi() {
        UserProfileDTO tecnicoActual = userFeignClient.obtenerMiPerfil();
        List<Ticket> tickets = ticketRepository.findByTecnicoAsignadoId(tecnicoActual.getId());
        return ticketMapper.toResponseDTOList(tickets);
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsCerrados() {
        return ticketMapper.toResponseDTOList(ticketRepository.findByEstado(EstadoTicket.CERRADO));
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsCerradosDesde(LocalDateTime fechaDesde) {
        List<Ticket> tickets = ticketRepository.findByEstadoAndFechaActualizacionAfter(EstadoTicket.CERRADO, fechaDesde);
        return ticketMapper.toResponseDTOList(tickets);
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsPendientesParaDashboard() {
        UserProfileDTO usuarioActual = userFeignClient.obtenerMiPerfil();

        if (usuarioActual.getRole() == Rol.ADMIN) {
            return ticketMapper.toResponseDTOList(ticketRepository.findByEstado(EstadoTicket.PENDIENTE));
        } else if (usuarioActual.getRole() == Rol.TECHNICIAN) {
            List<Ticket> ticketsAsignados = ticketRepository.findByTecnicoAsignadoId(usuarioActual.getId());
            return ticketMapper.toResponseDTOList(
                    ticketsAsignados.stream()
                            .filter(t -> t.getEstado() == EstadoTicket.PENDIENTE)
                            .toList()
            );
        }
        return List.of();
    }

    @Override
    public TicketResponseDTO crearTicket(TicketRequestDTO requestDTO) {
        try {
            if (requestDTO.getUsuarioReportaId() != null) userFeignClient.obtenerUsuarioPorId(requestDTO.getUsuarioReportaId());
            if (requestDTO.getEquipoAfectadoId() != null) equipoFeignClient.obtenerEquipoPorId(requestDTO.getEquipoAfectadoId());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Error de validación: El ID de usuario o equipo proporcionado no existe.");
        }

        Ticket newTicket = ticketMapper.toEntity(requestDTO);
        Ticket savedTicket = ticketRepository.save(newTicket);
        return ticketMapper.toResponseDTO(savedTicket);
    }

    @Override
    public TicketResponseDTO obtenerTicketPorId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + id));

        UsuarioResponseDTO usuarioReporta = null;
        if (ticket.getUsuarioReportaId() != null) {
            try {
                usuarioReporta = userFeignClient.obtenerUsuarioPorId(ticket.getUsuarioReportaId());
            } catch (FeignException.NotFound e) { /* Ignorar */ }
        }

        UsuarioResponseDTO tecnicoAsignado = null;
        if (ticket.getTecnicoAsignadoId() != null) {
            try {
                tecnicoAsignado = userFeignClient.obtenerUsuarioPorId(ticket.getTecnicoAsignadoId());
            } catch (FeignException.NotFound e) { /* Ignorar */ }
        }

        EquipoResponseDTO equipo = null;
        if (ticket.getEquipoAfectadoId() != null) {
            try {
                equipo = equipoFeignClient.obtenerEquipoPorId(ticket.getEquipoAfectadoId());
            } catch (FeignException.NotFound e) { /* Ignorar */ }
        }

        ComponenteResponseDTO componente = null;
        if (ticket.getComponenteId() != null) {
            try {
                componente = componenteFeignClient.obtenerComponentePorId(ticket.getComponenteId());
            } catch (FeignException.NotFound e) { /* Ignorar */ }
        }

        return ticketMapper.toResponseDTO(ticket, usuarioReporta, tecnicoAsignado, equipo, componente);
    }

    @Override
    public List<TicketResponseDTO> obtenerTodosLosTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ticketMapper.toResponseDTOList(tickets);
    }

    @Override
    @Transactional
    public TicketResponseDTO actualizarTicket(Long id, TicketRequestDTO requestDTO) {
        Ticket ticketExistente = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado para actualizar con ID: " + id));

        // CORRECCIÓN: Solo se bloquea si el estado es CERRADO
        if (ticketExistente.getEstado() == EstadoTicket.CERRADO && requestDTO.getEstado() != null && requestDTO.getEstado() != EstadoTicket.CERRADO) {
            throw new BusinessException("No se puede reabrir un ticket que ya está cerrado.");
        }

        String asunto = requestDTO.getAsunto() != null ? requestDTO.getAsunto() : ticketExistente.getAsunto();
        String descripcion = requestDTO.getDescripcion() != null ? requestDTO.getDescripcion() : ticketExistente.getDescripcion();
        PrioridadTicket prioridad = requestDTO.getPrioridad() != null ? requestDTO.getPrioridad() : ticketExistente.getPrioridad();
        EstadoTicket estado = requestDTO.getEstado() != null ? requestDTO.getEstado() : ticketExistente.getEstado();
        Long equipoAfectadoId = requestDTO.getEquipoAfectadoId(); // Permite ponerlo en null
        Long componenteId = requestDTO.getComponenteId(); // Permite ponerlo en null
        Long tecnicoAsignadoId = requestDTO.getTecnicoAsignadoId();
        String diagnostico = requestDTO.getDiagnostico() != null ? requestDTO.getDiagnostico() : ticketExistente.getDiagnostico();

        ticketRepository.updateTicket(
                id,
                asunto,
                descripcion,
                prioridad,
                estado,
                equipoAfectadoId,
                componenteId,
                tecnicoAsignadoId,
                diagnostico,
                LocalDateTime.now()
        );

        return obtenerTicketPorId(id);
    }

    @Override
    public void eliminarTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se pudo encontrar el ticket para eliminar con ID: " + id);
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsPorEstado(EstadoTicket estado) {
        List<Ticket> tickets = ticketRepository.findByEstado(estado);
        return ticketMapper.toResponseDTOList(tickets);
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsPorPrioridad(PrioridadTicket prioridad) {
        List<Ticket> tickets = ticketRepository.findByPrioridad(prioridad);
        return ticketMapper.toResponseDTOList(tickets);
    }

    @Override
    public List<TicketResponseDTO> obtenerTicketsPorUsuario(Long usuarioId) {
        List<Ticket> tickets = ticketRepository.findByUsuarioReportaId(usuarioId);
        return ticketMapper.toResponseDTOList(tickets);
    }



    @Override
    public List<EquipoResponseDTO> obtenerEquiposPorUsuarioReportaId(Long usuarioReportaId) {
        try {
            return equipoFeignClient.obtenerEquiposPorUsuario(usuarioReportaId);
        } catch (FeignException.NotFound e) {
            return List.of(); // Retorna lista vacía si no encuentra equipos
        } catch (Exception e) {
            // Manejo de otros errores de Feign, como timeout o servicio no disponible
            System.err.println("Error al obtener equipos para el usuario " + usuarioReportaId + ": " + e.getMessage());
            throw new BusinessException("Error al comunicarse con el servicio de gestión de activos.");
        }
    }
}

