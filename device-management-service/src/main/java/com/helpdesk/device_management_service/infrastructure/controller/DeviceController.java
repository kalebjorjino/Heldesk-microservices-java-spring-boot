package com.helpdesk.device_management_service.infrastructure.controller;

import com.helpdesk.device_management_service.application.ports.in.DeviceUseCase;
import com.helpdesk.device_management_service.domain.dto.DeviceResponseDTO;
import com.helpdesk.device_management_service.domain.dto.DeviceRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dispositivos")
public class DeviceController {

    private final DeviceUseCase deviceUseCase;

    public DeviceController(DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }

    @PostMapping
    public ResponseEntity<DeviceResponseDTO> crearDevice(@RequestBody DeviceRequestDTO requestDTO) {
        return new ResponseEntity<>(deviceUseCase.crearDevice(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(deviceUseCase.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(deviceUseCase.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> actualizarDevice(@PathVariable Long id, @RequestBody DeviceRequestDTO requestDTO) {
        return ResponseEntity.ok(deviceUseCase.actualizarDevice(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDevice(@PathVariable Long id) {
        deviceUseCase.eliminarDevice(id);
        return ResponseEntity.noContent().build();
    }
}
