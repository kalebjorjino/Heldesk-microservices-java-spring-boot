package com.helpdesk.user_management_service.application.ports.in;

import com.helpdesk.user_management_service.application.dto.UserProfileDTO;
import com.helpdesk.user_management_service.application.dto.UsuarioRequestDTO;
import com.helpdesk.user_management_service.application.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioUseCase {

    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO);

    UsuarioResponseDTO obtenerPorId(Long id);

    List<UsuarioResponseDTO> obtenerTodos();

    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO requestDTO);

    void eliminarUsuario(Long id);

    UserProfileDTO obtenerPorEmail(String email);
}
