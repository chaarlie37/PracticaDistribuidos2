package sd.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.a2.model.Aeropuerto;

import java.util.List;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {
    // Devolver todos los aeropuertos
    List<Aeropuerto> findAll();
    // Devolver un aeropuerto dado su nombre
    Aeropuerto findByNombre(String nombre);
}
