package com.helpdesk.asset_management_service.application.mappers;

import com.helpdesk.asset_management_service.domain.Equipo;
import com.helpdesk.asset_management_service.domain.dto.EquipoRequestDTO;
import com.helpdesk.asset_management_service.domain.dto.EquipoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipoMapper {

    /**
     * Convierte un DTO de solicitud a una entidad Equipo. (Sin cambios)
     */
    public Equipo toEntity(EquipoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Equipo equipo = new Equipo();
        equipo.setCodigoPatrimonial(dto.getCodigoPatrimonial());
        equipo.setSerie(dto.getSerie());
        equipo.setMarca(dto.getMarca());
        equipo.setModelo(dto.getModelo());
        equipo.setIp(dto.getIp());
        equipo.setDepartamento(dto.getDepartamento());
        equipo.setUnidad(dto.getUnidad());
        equipo.setUsuarioAsignadoId(dto.getUsuarioAsignadoId());
        equipo.setDeviceAsignadoId(dto.getDeviceAsignadoId());
        return equipo;
    }

    /**
     * Convierte una entidad Equipo a un DTO de respuesta.
     * FIX: Se cambia el constructor de 9 argumentos por el constructor sin argumentos
     * para evitar el conflicto con los 15 campos del EquipoResponseDTO.
     */
    public EquipoResponseDTO toResponseDTO(Equipo entity) {
        if (entity == null) {
            return null;
        }

        EquipoResponseDTO responseDTO = new EquipoResponseDTO(); // Uso de @NoArgsConstructor

        // Asignación de los 9 campos del Equipo mediante Setters
        responseDTO.setId(entity.getId());
        responseDTO.setCodigoPatrimonial(entity.getCodigoPatrimonial());
        responseDTO.setSerie(entity.getSerie());
        responseDTO.setMarca(entity.getMarca());
        responseDTO.setModelo(entity.getModelo());
        responseDTO.setIp(entity.getIp());
        responseDTO.setDepartamento(entity.getDepartamento());
        responseDTO.setUnidad(entity.getUnidad());
        responseDTO.setUsuarioAsignadoId(entity.getUsuarioAsignadoId());
        responseDTO.setDeviceAsignadoId(entity.getDeviceAsignadoId());

        // Los campos de enriquecimiento (ej. nombreCompletoUsuario) se inicializan en null
        // y serán llenados por el EquipoService.

        return responseDTO;
    }

    /**
     * Convierte una lista de entidades Equipo a una lista de DTOs de respuesta.
     */
    public List<EquipoResponseDTO> toResponseDTOList(List<Equipo> equipos) {
        return equipos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}