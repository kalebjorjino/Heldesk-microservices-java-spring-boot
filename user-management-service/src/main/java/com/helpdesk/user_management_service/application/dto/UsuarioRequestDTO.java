package com.helpdesk.user_management_service.application.dto;

import com.helpdesk.user_management_service.domain.Rol;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {
    // --- Campos de Identificación y Autenticación ---

    @NotBlank(message = "El código de empleado es obligatorio")
    @Size(min = 4, max = 20, message = "El código de empleado debe tener entre 4 y 20 caracteres")
    private String codigoEmpleado;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    private String email;


    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Rol role; // El JSON debe enviar "USER", "TECHNICIAN" o "ADMIN"

    // --- Campos de Información Personal ---

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener 8 dígitos numéricos")
    private String dni;

    // --- Campos Organizacionales ---

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    private String unidad; // Campo opcional (sin @NotBlank)

    private String area;   // Campo opcional (sin @NotBlank)
}
