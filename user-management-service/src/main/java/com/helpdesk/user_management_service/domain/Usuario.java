package com.helpdesk.user_management_service.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoEmpleado; // Identificador único de empleado

    private String nombreCompleto;

    @Column(unique = true)
    private String dni;

    @Email // Validación: debe tener formato de email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email; // Indispensable para login/notificaciones

    @NotBlank
    @Column(nullable = false)
    private String password; // Almacenará la contraseña hasheada

    @NotNull
    @Enumerated(EnumType.STRING) // Guarda el Enum como "USER", "ADMIN", etc.
    @Column(nullable = false)
    private Rol role;


    private String cargo;
    private String departamento;
    private String unidad;
    private String area;
}
