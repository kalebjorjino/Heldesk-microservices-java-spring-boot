package com.helpdesk.asset_management_service.application.ports.out;

import com.helpdesk.asset_management_service.domain.dto.DeviceDto;

public interface DevicePort {
    // Contrato para que la lógica de negocio obtenga los datos del dispositivo.
    DeviceDto fetchDeviceById(Long id);
}
