package com.helpdesk.asset_management_service.application.ports.in;

import com.helpdesk.asset_management_service.domain.dto.EquipoRequestDTO;
import com.helpdesk.asset_management_service.domain.dto.EquipoResponseDTO;

import java.util.List;

public interface EquipoUseCase {

    EquipoResponseDTO crearEquipo(EquipoRequestDTO requestDTO);

    EquipoResponseDTO obtenerPorId(Long id);

    List<EquipoResponseDTO> obtenerTodos();

    List<EquipoResponseDTO> obtenerEquiposPorUsuario(Long usuarioId);

    EquipoResponseDTO actualizarEquipo(Long id, EquipoRequestDTO requestDTO);

    void eliminarEquipo(Long id);
}
