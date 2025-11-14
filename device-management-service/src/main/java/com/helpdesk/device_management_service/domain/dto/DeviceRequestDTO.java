package com.helpdesk.device_management_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequestDTO {

    private String codigoPatrimonial;
    private String nombreComponente;
    private String especificaciones;
    private String marca;
    private String modelo;
    private String serie;


}
