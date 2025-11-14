package com.helpdesk.user_management_service.application.mappers;

import com.helpdesk.user_management_service.application.dto.UserProfileDTO;
import com.helpdesk.user_management_service.domain.Usuario;
import com.helpdesk.user_management_service.application.dto.UsuarioRequestDTO;
import com.helpdesk.user_management_service.application.dto.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    /**
     * Convierte un DTO de solicitud a una entidad Usuario.
     */
    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setCodigoEmpleado(dto.getCodigoEmpleado());
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setDni(dto.getDni());
        usuario.setEmail(dto.getEmail());
        usuario.setRole(dto.getRole());
        usuario.setCargo(dto.getCargo());
        usuario.setDepartamento(dto.getDepartamento());
        usuario.setUnidad(dto.getUnidad());
        usuario.setArea(dto.getArea());
        return usuario;
    }

    /**
     * Convierte una entidad Usuario a un DTO de respuesta.
     */
    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }
        return new UsuarioResponseDTO(
                entity.getId(),
                entity.getCodigoEmpleado(),
                entity.getNombreCompleto(),
                entity.getDni(),
                entity.getEmail(),
                entity.getRole(),
                entity.getCargo(),
                entity.getDepartamento(),
                entity.getUnidad(),
                entity.getArea()
        );
    }

    /**
     * Convierte una lista de entidades Usuario a una lista de DTOs de respuesta.
     */
    public List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Usuario a un DTO de perfil de usuario.
     */
    public UserProfileDTO toProfileDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }
        return new UserProfileDTO(
                entity.getId(),
                entity.getCodigoEmpleado(),
                entity.getNombreCompleto(),
                entity.getDni(),
                entity.getEmail(),
                entity.getRole(),
                entity.getCargo(),
                entity.getDepartamento(),
                entity.getUnidad(),
                entity.getArea()
        );
    }
}
