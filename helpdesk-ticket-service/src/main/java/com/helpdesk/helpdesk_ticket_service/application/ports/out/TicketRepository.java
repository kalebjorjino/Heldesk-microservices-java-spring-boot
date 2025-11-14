package com.helpdesk.helpdesk_ticket_service.application.ports.out;

import com.helpdesk.helpdesk_ticket_service.domain.dto.TicketStateStatsDTO;
import com.helpdesk.helpdesk_ticket_service.domain.Ticket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.EstadoTicket;
import com.helpdesk.helpdesk_ticket_service.domain.enums.PrioridadTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT new com.helpdesk.helpdesk_ticket_service.domain.dto.TicketStateStatsDTO(t.estado, COUNT(t)) FROM Ticket t GROUP BY t.estado")
    List<TicketStateStatsDTO> countTicketsByEstado();

    Long countByEstadoAndFechaCierreAfter(EstadoTicket estado, LocalDateTime fechaCierre);

    List<Ticket> findByEstado(EstadoTicket estado);

    List<Ticket> findByPrioridad(PrioridadTicket prioridad);

    List<Ticket> findByUsuarioReportaId(Long usuarioId);

    List<Ticket> findByTecnicoAsignadoId(Long tecnicoId);

    List<Ticket> findByEstadoAndFechaActualizacionAfter(EstadoTicket estado, LocalDateTime fechaActualizacion);


    @Modifying
    @Query("UPDATE Ticket t SET " +
            "t.asunto = :asunto, " +
            "t.descripcion = :descripcion, " +
            "t.prioridad = :prioridad, " +
            "t.estado = :estado, " +
            "t.equipoAfectadoId = :equipoAfectadoId, " +
            "t.componenteId = :componenteId, " +
            "t.tecnicoAsignadoId = :tecnicoAsignadoId, " +
            "t.diagnostico = :diagnostico, " +
            "t.fechaActualizacion = :fechaActualizacion " +
            "WHERE t.id = :id")
    void updateTicket(@Param("id") Long id,
                      @Param("asunto") String asunto,
                      @Param("descripcion") String descripcion,
                      @Param("prioridad") PrioridadTicket prioridad,
                      @Param("estado") EstadoTicket estado,
                      @Param("equipoAfectadoId") Long equipoAfectadoId,
                      @Param("componenteId") Long componenteId,
                      @Param("tecnicoAsignadoId") Long tecnicoAsignadoId,
                      @Param("diagnostico") String diagnostico,
                      @Param("fechaActualizacion") LocalDateTime fechaActualizacion);
}
