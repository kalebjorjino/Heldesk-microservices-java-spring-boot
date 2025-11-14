package com.helpdesk.asset_management_service.infrastructure.controller;

import com.helpdesk.asset_management_service.application.ports.in.EquipoUseCase;
import com.helpdesk.asset_management_service.domain.dto.EquipoRequestDTO;
import com.helpdesk.asset_management_service.domain.dto.EquipoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoUseCase equipoUseCase;

    public EquipoController(EquipoUseCase equipoUseCase) {
        this.equipoUseCase = equipoUseCase;
    }

    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crearEquipo(@RequestBody EquipoRequestDTO requestDTO) {
        return new ResponseEntity<>(equipoUseCase.crearEquipo(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipoUseCase.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> obtenerTodos(@RequestParam(required = false) Long usuarioId) {
        if (usuarioId != null) {
            return ResponseEntity.ok(equipoUseCase.obtenerEquiposPorUsuario(usuarioId));
        }
        return ResponseEntity.ok(equipoUseCase.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizarEquipo(@PathVariable Long id, @RequestBody EquipoRequestDTO requestDTO) {
        return ResponseEntity.ok(equipoUseCase.actualizarEquipo(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        equipoUseCase.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
