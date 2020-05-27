package sd.a2.repositories;

import org.springframework.stereotype.Repository;
import sd.a2.model.Aeropuerto;

import java.util.List;

@Repository
public interface AeropuertoRepository {
    Aeropuerto findByCodigo(String codigo);
    List<Aeropuerto> findAll();
    List<Aeropuerto> findByCiudad(String ciudad);
}
