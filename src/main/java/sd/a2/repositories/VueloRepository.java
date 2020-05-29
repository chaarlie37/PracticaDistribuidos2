package sd.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.a2.model.Aeropuerto;
import sd.a2.model.Vuelo;

import java.util.Date;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Integer> {
      Vuelo findByCodigo(String codigo);
      List<Vuelo> findAll();
      List<Vuelo> findBySalidaBetween(Date inicio, Date fin);
      List<Vuelo> findBySalidaBetweenAndOrigenAndDestino(Date inicio, Date fin, Aeropuerto origen, Aeropuerto destino);
}
