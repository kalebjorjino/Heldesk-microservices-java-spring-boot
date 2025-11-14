package com.helpdesk.device_management_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispositivos")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoPatrimonial;

    private String nombreComponente;
    private String especificaciones;
    private String marca;
    private String modelo;

    @Column(unique = true)
    private String serie;
}
