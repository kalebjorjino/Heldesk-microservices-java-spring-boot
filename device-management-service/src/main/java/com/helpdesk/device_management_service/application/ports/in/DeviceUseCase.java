package com.helpdesk.device_management_service.application.ports.in;

import com.helpdesk.device_management_service.domain.dto.DeviceRequestDTO;
import com.helpdesk.device_management_service.domain.dto.DeviceResponseDTO;

import java.util.List;

public interface DeviceUseCase {

    DeviceResponseDTO crearDevice(DeviceRequestDTO requestDTO);
    DeviceResponseDTO obtenerPorId(Long id);
    List<DeviceResponseDTO> obtenerTodos();

    DeviceResponseDTO actualizarDevice(Long id, DeviceRequestDTO requestDTO);

    void eliminarDevice(Long id);
}
