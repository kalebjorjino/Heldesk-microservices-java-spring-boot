package com.helpdesk.helpdesk_ticket_service.domain.dto.external;

import com.helpdesk.helpdesk_ticket_service.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String nombreCompleto;
    private String email;
    private Rol role;
}
