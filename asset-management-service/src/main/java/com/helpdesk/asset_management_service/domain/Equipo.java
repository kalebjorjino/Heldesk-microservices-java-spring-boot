package com.helpdesk.asset_management_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipos")
public class Equipo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigoPatrimonial;

    @Column(unique = true)
    private String serie;

    private String marca;
    private String modelo;

    @Column(unique = true)
    private String ip;


    private String departamento;
    private String unidad;
    private Long usuarioAsignadoId;
    private Long deviceAsignadoId;


}
