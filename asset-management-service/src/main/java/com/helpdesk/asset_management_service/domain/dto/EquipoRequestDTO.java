package com.helpdesk.asset_management_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoRequestDTO {
    private String codigoPatrimonial;
    private String serie;
    private String marca;
    private String modelo;
    private String ip;
    private String departamento;
    private String unidad;
    private Long usuarioAsignadoId;
    private Long deviceAsignadoId;
}
