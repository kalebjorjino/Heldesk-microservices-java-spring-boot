package com.helpdesk.asset_management_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {

    // El ID primario para Feign Client
    private Long id;

    // Campos de identificación y organizacionales (contrato con el servicio de Dispositivos)
    private String codigoPatrimonial;
    private String nombreComponente;
    private String especificaciones;
    private String marca;
    private String modelo;
    private String serie;

}
