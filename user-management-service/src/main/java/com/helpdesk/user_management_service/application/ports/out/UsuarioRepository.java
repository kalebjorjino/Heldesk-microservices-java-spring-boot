package com.helpdesk.user_management_service.application.ports.out;

import com.helpdesk.user_management_service.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Contrato para validar unicidad en una sola consulta
    Optional<Usuario> findFirstByCodigoEmpleadoOrDniOrEmail(String codigoEmpleado, String dni, String email);

    // Métodos específicos para encontrar por un solo campo, pueden ser útiles en otros contextos
    Usuario findByCodigoEmpleado(String codigoEmpleado);
    Usuario findByDni(String dni);
    Usuario findByEmail(String email);
}
