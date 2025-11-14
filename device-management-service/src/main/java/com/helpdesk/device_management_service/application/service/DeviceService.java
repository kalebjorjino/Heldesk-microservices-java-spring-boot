package com.helpdesk.device_management_service.application.service;

import com.helpdesk.device_management_service.application.exceptions.ResourceNotFoundException;
import com.helpdesk.device_management_service.application.mappers.DeviceMapper;
import com.helpdesk.device_management_service.application.ports.in.DeviceUseCase;
import com.helpdesk.device_management_service.application.ports.out.DeviceRepository;
import com.helpdesk.device_management_service.domain.Device;
import com.helpdesk.device_management_service.domain.dto.DeviceRequestDTO;
import com.helpdesk.device_management_service.domain.dto.DeviceResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService implements DeviceUseCase {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public DeviceResponseDTO crearDevice(DeviceRequestDTO requestDTO) {
        if (deviceRepository.findByCodigoPatrimonial(requestDTO.getCodigoPatrimonial()) != null) {
            throw new IllegalArgumentException("Error: El código patrimonial ya existe.");
        }
        if (deviceRepository.findBySerie(requestDTO.getSerie()) != null) {
            throw new IllegalArgumentException("Error: La serie ya está registrada.");
        }

        Device newDevice = deviceMapper.toEntity(requestDTO);
        Device savedDevice = deviceRepository.save(newDevice);
        return deviceMapper.toResponseDTO(savedDevice);
    }

    @Override
    public DeviceResponseDTO obtenerPorId(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device no encontrado con ID: " + id));
        return deviceMapper.toResponseDTO(device);
    }

    @Override
    public List<DeviceResponseDTO> obtenerTodos() {
        List<Device> devices = deviceRepository.findAll();
        return deviceMapper.toResponseDTOList(devices);
    }

    @Override
    public DeviceResponseDTO actualizarDevice(Long id, DeviceRequestDTO requestDTO) {
        return deviceRepository.findById(id).map(deviceExistente -> {
            deviceExistente.setCodigoPatrimonial(requestDTO.getCodigoPatrimonial());
            deviceExistente.setNombreComponente(requestDTO.getNombreComponente());
            deviceExistente.setEspecificaciones(requestDTO.getEspecificaciones());
            deviceExistente.setMarca(requestDTO.getMarca());
            deviceExistente.setModelo(requestDTO.getModelo());
            deviceExistente.setSerie(requestDTO.getSerie());

            Device deviceActualizado = deviceRepository.save(deviceExistente);
            return deviceMapper.toResponseDTO(deviceActualizado);
        }).orElseThrow(() -> new ResourceNotFoundException("Device no encontrado para actualizar con ID: " + id));
    }


    @Override
    public void eliminarDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se pudo encontrar el device para eliminar con ID: " + id);
        }
        deviceRepository.deleteById(id);
    }
}
