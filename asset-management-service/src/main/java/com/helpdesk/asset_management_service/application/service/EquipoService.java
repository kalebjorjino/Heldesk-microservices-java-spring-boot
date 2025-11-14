package com.helpdesk.asset_management_service.application.service;

import com.helpdesk.asset_management_service.application.exceptions.ResourceNotFoundException;
import com.helpdesk.asset_management_service.application.mappers.EquipoMapper;
import com.helpdesk.asset_management_service.application.ports.in.EquipoUseCase;
import com.helpdesk.asset_management_service.application.ports.out.DevicePort;
import com.helpdesk.asset_management_service.application.ports.out.EquipoRepository;
import com.helpdesk.asset_management_service.application.ports.out.UserPort;
import com.helpdesk.asset_management_service.domain.Equipo;
import com.helpdesk.asset_management_service.domain.dto.EquipoRequestDTO;
import com.helpdesk.asset_management_service.domain.dto.EquipoResponseDTO;
import com.helpdesk.asset_management_service.domain.dto.UsuarioDto;
import com.helpdesk.asset_management_service.domain.dto.DeviceDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService implements EquipoUseCase {

    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;
    private final UserPort userPort;
    private final DevicePort devicePort;


    public EquipoService(EquipoRepository equipoRepository, EquipoMapper equipoMapper, UserPort userPort, DevicePort devicePort) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
        this.userPort = userPort;
        this.devicePort = devicePort;
    }

    @Override
    public EquipoResponseDTO crearEquipo(EquipoRequestDTO requestDTO) {
        if (equipoRepository.existsByCodigoPatrimonial(requestDTO.getCodigoPatrimonial())) {
            throw new IllegalArgumentException("Error: El código patrimonial ya existe.");
        }
        if (equipoRepository.existsByIp(requestDTO.getIp())) {
            throw new IllegalArgumentException("Error: La IP ya está asignada a otro equipo.");
        }
        if (equipoRepository.existsBySerie(requestDTO.getSerie())) {
            throw new IllegalArgumentException("Error: La serie ya está asignada a otro equipo.");
        }
        if (requestDTO.getDeviceAsignadoId() != null &&
                equipoRepository.existsByDeviceAsignadoId(requestDTO.getDeviceAsignadoId())) {
            throw new IllegalArgumentException("Error: El dispositivo ya está asignado a otro equipo.");
        }
        if (requestDTO.getUsuarioAsignadoId() != null &&
                equipoRepository.existsByUsuarioAsignadoId(requestDTO.getUsuarioAsignadoId())) {
            throw new IllegalArgumentException("Error: El usuario ya está asignado a otro equipo.");
        }

        Equipo newEquipo = equipoMapper.toEntity(requestDTO);
        Equipo savedEquipo = equipoRepository.save(newEquipo);
        return equipoMapper.toResponseDTO(savedEquipo);
    }

    /**
     * Obtiene un equipo por ID y enriquece la respuesta con datos del usuario
     * obtenidos del USER-MANAGEMENT-SERVICE.
     */
    @Override
    public EquipoResponseDTO obtenerPorId(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con ID: " + id));

        EquipoResponseDTO responseDTO = equipoMapper.toResponseDTO(equipo);

        // Lógica de ENRIQUECIMIENTO DE DATOS
        if (equipo.getUsuarioAsignadoId() != null) {
            try {
                // Llama al Puerto (Adaptador Feign)
                UsuarioDto usuarioInfo = userPort.fetchUsuarioById(equipo.getUsuarioAsignadoId());

                // Mapeo de datos (Solo si el usuario fue encontrado y no es nulo)
                if (usuarioInfo != null) {
                    // ASIGNACIÓN DE TODOS LOS CAMPOS DEL USUARIO AL DTO DE RESPUESTA
                    responseDTO.setCodigoEmpleadoUsuario(usuarioInfo.getCodigoEmpleado());
                    responseDTO.setNombreCompletoUsuario(usuarioInfo.getNombreCompleto());
                    responseDTO.setDniUsuario(usuarioInfo.getDni());
                    responseDTO.setCargoUsuario(usuarioInfo.getCargo());
                    responseDTO.setDepartamentoUsuario(usuarioInfo.getDepartamento());
                    responseDTO.setUnidad(usuarioInfo.getUnidad());
                    responseDTO.setAreaUsuario(usuarioInfo.getArea());
                }
            } catch (Exception e) {
                // Manejar la excepción lanzada por el UserFeignAdapter (ej. RuntimeException)
                System.err.println("Advertencia: Fallo la obtención de información del usuario ID " + equipo.getUsuarioAsignadoId() + ": " + e.getMessage());
                // Los campos de usuario en el DTO de respuesta quedarán como null.
            }
        }

        if (equipo.getDeviceAsignadoId() != null) {
            try {
                // Llama al Puerto (Adaptador Feign)
                DeviceDto deviceInfo = devicePort.fetchDeviceById(equipo.getDeviceAsignadoId());

                // Mapeo de datos (Solo si el usuario fue encontrado y no es nulo)
                if (deviceInfo != null) {
                    // ASIGNACIÓN DE TODOS LOS CAMPOS DEL DISPOTIVOS AL DTO DE RESPUESTA
                    responseDTO.setCodigoPatrimonialDevice(deviceInfo.getCodigoPatrimonial());
                    responseDTO.setNombreComponenteDevice(deviceInfo.getNombreComponente());
                    responseDTO.setEspecificacionesDevice(deviceInfo.getEspecificaciones());
                    responseDTO.setMarcaDevice(deviceInfo.getMarca());
                    responseDTO.setModeloDevice(deviceInfo.getModelo());
                    responseDTO.setSerie(deviceInfo.getSerie());
                }
            } catch (Exception e) {
                // Manejar la excepción lanzada por el UserFeignAdapter (ej. RuntimeException)
                System.err.println("Advertencia: Fallo la obtención de información del dispositivo ID " + equipo.getDeviceAsignadoId() + ": " + e.getMessage());
                // Los campos de usuario en el DTO de respuesta quedarán como null.
            }
        }

        return responseDTO;
    }

    @Override
    public List<EquipoResponseDTO> obtenerTodos() {
        List<Equipo> equipos = equipoRepository.findAll();
        return equipoMapper.toResponseDTOList(equipos);
    }

    @Override
    public List<EquipoResponseDTO> obtenerEquiposPorUsuario(Long usuarioId) {
        List<Equipo> equipos = equipoRepository.findAllByUsuarioAsignadoId(usuarioId);
        return equipoMapper.toResponseDTOList(equipos);
    }

    @Override
    public EquipoResponseDTO actualizarEquipo(Long id, EquipoRequestDTO requestDTO) {
        return equipoRepository.findById(id).map(equipoExistente -> {

            // Si cambia de usuario, validar que no esté asignado ya en otro equipo
            if (requestDTO.getUsuarioAsignadoId() != null) {
                Equipo equipoConUsuario = equipoRepository.findByUsuarioAsignadoId(requestDTO.getUsuarioAsignadoId());
                if (equipoConUsuario != null && !equipoConUsuario.getId().equals(id)) {
                    throw new IllegalArgumentException("Error: El usuario ya tiene asignado otro equipo.");
                }
            }
            if (requestDTO.getDeviceAsignadoId() != null) {
                Equipo equipoConDevice = equipoRepository.findByDeviceAsignadoId(requestDTO.getDeviceAsignadoId());
                if (equipoConDevice != null && !equipoConDevice.getId().equals(id)) {
                    throw new IllegalArgumentException("Error: El dispositivo ya tiene asignado otro equipo.");
                }
            }

            // Actualiza la entidad existente con los datos del DTO
            equipoExistente.setCodigoPatrimonial(requestDTO.getCodigoPatrimonial());
            equipoExistente.setSerie(requestDTO.getSerie());
            equipoExistente.setMarca(requestDTO.getMarca());
            equipoExistente.setModelo(requestDTO.getModelo());
            equipoExistente.setIp(requestDTO.getIp());
            equipoExistente.setDepartamento(requestDTO.getDepartamento());
            equipoExistente.setUnidad(requestDTO.getUnidad());
            equipoExistente.setUsuarioAsignadoId(requestDTO.getUsuarioAsignadoId());
            equipoExistente.setDeviceAsignadoId(requestDTO.getDeviceAsignadoId());

            Equipo equipoActualizado = equipoRepository.save(equipoExistente);
            return equipoMapper.toResponseDTO(equipoActualizado);
        }).orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado para actualizar con ID: " + id));
    }

    @Override
    public void eliminarEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se pudo encontrar el equipo para eliminar con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }
}
