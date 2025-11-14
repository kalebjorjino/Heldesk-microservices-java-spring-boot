package com.helpdesk.user_management_service.infrastructure.controller;

import com.helpdesk.user_management_service.application.dto.AuthRequestDTO;
import com.helpdesk.user_management_service.application.dto.AuthResponseDTO;
import com.helpdesk.user_management_service.application.dto.UserProfileDTO;
import com.helpdesk.user_management_service.application.ports.in.UsuarioUseCase;
import com.helpdesk.user_management_service.application.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioUseCase usuarioUseCase;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UsuarioUseCase usuarioUseCase) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioUseCase = usuarioUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody AuthRequestDTO authRequest) {

        // 1. Spring Security valida las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        // 2. Si es exitoso, establece la autenticación
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Genera el token JWT
        String token = jwtTokenProvider.generateToken(authentication);

        // 4. Obtiene los detalles del usuario para la respuesta
        UserProfileDTO userProfile = usuarioUseCase.obtenerPorEmail(authRequest.getEmail());

        // 5. Crea la respuesta completa
        AuthResponseDTO authResponse = new AuthResponseDTO(token, "Bearer", userProfile);

        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile(Authentication authentication) {
        // El objeto Authentication es inyectado por Spring Security y contiene los detalles del usuario autenticado
        String email = authentication.getName();
        UserProfileDTO userProfile = usuarioUseCase.obtenerPorEmail(email);
        return ResponseEntity.ok(userProfile);
    }
}
