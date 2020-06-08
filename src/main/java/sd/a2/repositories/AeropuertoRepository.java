package sd.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.a2.model.Aeropuerto;

import java.util.List;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {
    // Devolver todos los aeropuertos
    List<Aeropuerto> findAll();
}
