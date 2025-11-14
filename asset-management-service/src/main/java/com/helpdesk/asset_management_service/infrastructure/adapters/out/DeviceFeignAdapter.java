package com.helpdesk.asset_management_service.infrastructure.adapters.out;

import com.helpdesk.asset_management_service.application.ports.out.DevicePort;
import com.helpdesk.asset_management_service.domain.dto.DeviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// La interfaz técnica de Feign para la comunicación HTTP.
@FeignClient(name = "DEVICE-MANAGEMENT-SERVICE")
interface InternalDeviceFeignClient {
    @GetMapping("/dispositivos/{id}")
    DeviceDto getDeviceById(@PathVariable("id") Long id);
}

@Component
public class DeviceFeignAdapter implements DevicePort{

    private final InternalDeviceFeignClient feignClient;

    public DeviceFeignAdapter(InternalDeviceFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public DeviceDto fetchDeviceById(Long id) {
        if (id == null) {
            return null;
        }

        try {
            return feignClient.getDeviceById(id);
        } catch (Exception e) {
            // Manejo de errores de infraestructura
            System.err.println("Error al contactar al servicio de dispositivos: " + e.getMessage());


            throw new RuntimeException("Servicio de dispositivos no disponible o ID no encontrado.", e);
        }
    }
}
