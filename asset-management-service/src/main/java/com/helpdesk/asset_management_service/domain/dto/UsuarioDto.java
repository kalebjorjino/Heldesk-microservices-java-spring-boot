package com.helpdesk.asset_management_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    // El ID primario para Feign Client
    private Long id;

    // Campos de identificación y organizacionales (contrato con el servicio de Usuarios)
    private String codigoEmpleado;
    private String nombreCompleto;
    private String dni;
    // Campos de la estructura laboral
    private String cargo;
    private String departamento;
    private String unidad;
    private String area;
}
