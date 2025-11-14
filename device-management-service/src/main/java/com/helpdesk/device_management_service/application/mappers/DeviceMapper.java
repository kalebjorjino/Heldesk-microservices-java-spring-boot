package com.helpdesk.device_management_service.application.mappers;

import com.helpdesk.device_management_service.domain.Device;
import com.helpdesk.device_management_service.domain.dto.DeviceRequestDTO;
import com.helpdesk.device_management_service.domain.dto.DeviceResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Device device = new Device();
        device.setCodigoPatrimonial(dto.getCodigoPatrimonial());
        device.setNombreComponente(dto.getNombreComponente());
        device.setEspecificaciones(dto.getEspecificaciones());
        device.setMarca(dto.getMarca());
        device.setModelo(dto.getModelo());
        device.setSerie(dto.getSerie());

        return device;
    }

    public DeviceResponseDTO toResponseDTO(Device entity){
        if (entity == null) {
            return null;
        }
        return new DeviceResponseDTO(
                entity.getId(),
                entity.getCodigoPatrimonial(),
                entity.getNombreComponente(),
                entity.getEspecificaciones(),
                entity.getMarca(),
                entity.getModelo(),
                entity.getSerie()
        );
    }

    public List<DeviceResponseDTO> toResponseDTOList(List<Device> devices) {
        return devices.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
