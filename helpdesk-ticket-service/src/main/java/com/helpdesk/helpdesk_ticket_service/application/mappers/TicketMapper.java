package com.helpdesk.helpdesk_ticket_service.application.mappers;

import com.helpdesk.helpdesk_ticket_service.domain.Ticket;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketRequestDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.ComponenteResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.EquipoResponseDTO;
import com.helpdesk.helpdesk_ticket_service.domain.dto.external.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketRequestDTO dto) {
        if (dto == null) return null;
        Ticket ticket = new Ticket();
        ticket.setAsunto(dto.getAsunto());
        ticket.setDescripcion(dto.getDescripcion());
        ticket.setPrioridad(dto.getPrioridad());
        ticket.setUsuarioReportaId(dto.getUsuarioReportaId());
        ticket.setEquipoAfectadoId(dto.getEquipoAfectadoId());
        ticket.setComponenteId(dto.getComponenteId());
        ticket.setDiagnostico(dto.getDiagnostico());
        return ticket;
    }

    public TicketResponseDTO toResponseDTO(Ticket entity) {
        if (entity == null) return null;
        return new TicketResponseDTO(
                entity.getId(),
                entity.getAsunto(),
                entity.getDescripcion(),
                entity.getEstado(),
                entity.getPrioridad(),
                entity.getFechaCreacion(),
                entity.getDiagnostico(),
                entity.getUsuarioReportaId(),
                null, // nombreUsuarioReporta
                entity.getEquipoAfectadoId(),
                null, // detallesEquipo
                entity.getTecnicoAsignadoId(),
                null, // nombreTecnicoAsignado
                entity.getComponenteId(), // <-- CAMPO AÑADIDO
                null // detallesComponente
        );
    }

    public TicketResponseDTO toResponseDTO(Ticket ticket, UsuarioResponseDTO usuarioReporta, UsuarioResponseDTO tecnicoAsignado, EquipoResponseDTO equipo, ComponenteResponseDTO componente) {
        if (ticket == null) return null;

        TicketResponseDTO dto = toResponseDTO(ticket);

        if (usuarioReporta != null) {
            dto.setNombreUsuarioReporta(usuarioReporta.getNombreCompleto());
        }
        if (tecnicoAsignado != null) {
            dto.setNombreTecnicoAsignado(tecnicoAsignado.getNombreCompleto());
        }
        if (equipo != null) {
            dto.setDetallesEquipo(String.format("%s %s (%s)", equipo.getMarca(), equipo.getModelo(), equipo.getCodigoPatrimonial()));
        }
        if (componente != null) {
            dto.setDetallesComponente(componente.getNombre());
        }

        return dto;
    }

    public List<TicketResponseDTO> toResponseDTOList(List<Ticket> tickets) {
        return tickets.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
