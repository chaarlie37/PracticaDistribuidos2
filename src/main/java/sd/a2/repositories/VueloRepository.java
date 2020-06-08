package sd.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.a2.model.Aeropuerto;
import sd.a2.model.Vuelo;

import java.util.Date;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Integer> {
      // Devolver todos los vuelos
      List<Vuelo> findAll();
      // Devolver vuelos dada una fecha, origen y destino
      // Como DATE incluye la hora, hay que consultar los vuelos entre dos "fechas",
      // la fecha dada a las 00:00 y la fecha dada a las 23:59 (es decir, las 24h que dura el dia)
      List<Vuelo> findBySalidaBetweenAndOrigenAndDestino(Date inicio, Date fin, String origen, String destino);
}
