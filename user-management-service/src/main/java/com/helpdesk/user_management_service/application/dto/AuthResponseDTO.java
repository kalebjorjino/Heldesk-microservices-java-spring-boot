package com.helpdesk.user_management_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String tokenType = "Bearer";
    private UserProfileDTO userInfo;

    public AuthResponseDTO(String token) {
        this.token = token;
    }
}
