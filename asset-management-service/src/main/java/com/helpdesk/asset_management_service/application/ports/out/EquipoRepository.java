package com.helpdesk.asset_management_service.application.ports.out;

import com.helpdesk.asset_management_service.domain.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Equipo findByCodigoPatrimonial(String codigoPatrimonial);
    Equipo findBySerie(String serie);
    Equipo findByDeviceAsignadoId(Long deviceAsignadoId);
    Equipo findByUsuarioAsignadoId(Long usuarioAsignadoId);
    Equipo findByIp(String ip);

    List<Equipo> findAllByUsuarioAsignadoId(Long usuarioAsignadoId);

    boolean existsByCodigoPatrimonial(String codigoPatrimonial);
    boolean existsByIp(String ip);
    boolean existsBySerie(String serie);
    boolean existsByDeviceAsignadoId(Long deviceAsignadoId);
    boolean existsByUsuarioAsignadoId(Long usuarioAsignadoId);



}
