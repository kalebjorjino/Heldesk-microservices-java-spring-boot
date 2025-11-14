package com.helpdesk.asset_management_service.application.ports.out;

import com.helpdesk.asset_management_service.domain.dto.UsuarioDto;

public interface UserPort {
    // Contrato para que la lógica de negocio obtenga los datos del usuario.
    UsuarioDto fetchUsuarioById(Long id);
}
