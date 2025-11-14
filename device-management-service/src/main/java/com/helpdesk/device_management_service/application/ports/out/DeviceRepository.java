package com.helpdesk.device_management_service.application.ports.out;

import com.helpdesk.device_management_service.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    // Contrato para validar unicidad
    Device findByCodigoPatrimonial(String codigoPatrimonial);
    Device findBySerie(String serie);
}
