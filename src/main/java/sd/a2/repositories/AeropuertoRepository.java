package sd.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.a2.model.Aeropuerto;

import java.util.List;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {
    Aeropuerto findByCodigo(String codigo);
    List<Aeropuerto> findAll();
    List<Aeropuerto> findByCiudadIgnoreCase(String ciudad);
}
