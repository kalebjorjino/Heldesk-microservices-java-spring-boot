package com.helpdesk.user_management_service.application.dto;

import com.helpdesk.user_management_service.domain.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String codigoEmpleado;
    private String nombreCompleto;
    private String dni;
    private String email;
    private Rol role;
    private String cargo;
    private String departamento;
    private String unidad;
    private String area;
}
