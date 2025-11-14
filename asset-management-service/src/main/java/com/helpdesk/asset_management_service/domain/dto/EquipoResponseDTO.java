package com.helpdesk.asset_management_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoResponseDTO {
    private Long id;
    private String codigoPatrimonial;
    private String serie;
    private String marca;
    private String modelo;
    private String ip;
    private String departamento;
    private String unidad;

    // Referencia al ID (necesario para la lógica)
    private Long usuarioAsignadoId;

    private Long deviceAsignadoId;

    // ----------------------------------------------------
    // CAMPOS DEL USUARIO (ENRIQUECIMIENTO REMOTO VÍA FEIGN)
    // ----------------------------------------------------
    private String codigoEmpleadoUsuario;
    private String nombreCompletoUsuario;
    private String dniUsuario;
    private String cargoUsuario;
    private String departamentoUsuario;
    private String areaUsuario;

    // CAMPOS DE LOS COMPONENTES

    private String codigoPatrimonialDevice;
    private String nombreComponenteDevice;
    private String especificacionesDevice;
    private String marcaDevice;
    private String modeloDevice;
    private String serieDevice;
}
