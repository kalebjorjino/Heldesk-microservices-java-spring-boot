package com.helpdesk.user_management_service.application.service;

import com.helpdesk.user_management_service.application.dto.UserProfileDTO;
import com.helpdesk.user_management_service.application.exceptions.ResourceNotFoundException;
import com.helpdesk.user_management_service.application.mappers.UsuarioMapper;
import com.helpdesk.user_management_service.application.ports.in.UsuarioUseCase;
import com.helpdesk.user_management_service.application.ports.out.UsuarioRepository;
import com.helpdesk.user_management_service.domain.Usuario;
import com.helpdesk.user_management_service.application.dto.UsuarioRequestDTO;
import com.helpdesk.user_management_service.application.dto.UsuarioResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO) {

        // Validación de unicidad en una sola consulta
        Optional<Usuario> existingUser = usuarioRepository.findFirstByCodigoEmpleadoOrDniOrEmail(
                requestDTO.getCodigoEmpleado(), requestDTO.getDni(), requestDTO.getEmail());

        if (existingUser.isPresent()) {
            Usuario user = existingUser.get();
            if (user.getCodigoEmpleado().equals(requestDTO.getCodigoEmpleado())) {
                throw new IllegalArgumentException("Error: El código de empleado ya existe.");
            }
            if (user.getDni().equals(requestDTO.getDni())) {
                throw new IllegalArgumentException("Error: El DNI ya está registrado.");
            }
            if (user.getEmail().equals(requestDTO.getEmail())) {
                throw new IllegalArgumentException("Error: El Email ya está registrado.");
            }
        }

        Usuario newUsuario = usuarioMapper.toEntity(requestDTO);

        // Hashear la contraseña ANTES de guardar
        newUsuario.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        Usuario savedUsuario = usuarioRepository.save(newUsuario);
        return usuarioMapper.toResponseDTO(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        // Usamos el mapper para convertir cada elemento de la lista
        return usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO requestDTO) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {

            // Lógica de actualización completa

            // Actualiza los datos organizacionales
            usuarioExistente.setCodigoEmpleado(requestDTO.getCodigoEmpleado());
            usuarioExistente.setNombreCompleto(requestDTO.getNombreCompleto());
            usuarioExistente.setDni(requestDTO.getDni());
            usuarioExistente.setCargo(requestDTO.getCargo());
            usuarioExistente.setDepartamento(requestDTO.getDepartamento());
            usuarioExistente.setUnidad(requestDTO.getUnidad());
            usuarioExistente.setArea(requestDTO.getArea());

            // Actualiza los datos de cuenta
            usuarioExistente.setEmail(requestDTO.getEmail());
            usuarioExistente.setRole(requestDTO.getRole());

            // Actualiza la contraseña SÓLO si se provee una nueva
            if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
                usuarioExistente.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
            }

            Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
            return usuarioMapper.toResponseDTO(usuarioActualizado);

        }).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado para actualizar con ID: " + id));
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se pudo encontrar el usuario para eliminar con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con email: " + email);
        }
        return usuarioMapper.toProfileDTO(usuario);
    }
}
