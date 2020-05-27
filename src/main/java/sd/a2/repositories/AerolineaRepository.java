package sd.a2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.a2.model.Aerolinea;

import java.util.List;

@Repository
public interface AerolineaRepository extends JpaRepository<Aerolinea, Integer> {
    Aerolinea findByCodigo(String codigo);
    List<Aerolinea> findAll();

}
