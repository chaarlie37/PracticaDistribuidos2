package sd.a2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.a2.model.Aerolinea;

import java.util.List;

@Repository
public interface AerolineaRepository extends JpaRepository<Aerolinea, Integer> {
    // Devolver una aerolinea dado su codigo
    Aerolinea findByCodigo(String codigo);
    // Devolver todas las aerolineas
    List<Aerolinea> findAll();
}
