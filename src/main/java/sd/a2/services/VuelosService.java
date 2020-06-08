package sd.a2.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Vuelo;
import sd.a2.repositories.VueloRepository;
import java.util.Date;
import java.util.List;

@Component
// Clase auxiliar para gestionar el respositorio de vuelos
public class VuelosService {

    @Autowired
    // Repositorio de vuelos
    private VueloRepository vueloRepository;

    public VuelosService(){

    }


    // Devolver vuelos dada una fecha, origen y destino
    public List<Vuelo> getVuelosFechaOrigenDestino(Date fecha, String origen, String destino){
        // Como DATE incluye la hora, hay que consultar los vuelos entre dos "fechas",
        // la fecha dada a las 00:00 y la fecha dada a las 23:59 (es decir, las 24h que dura el dia)
        Date finDia = new Date(fecha.getTime() + 24 * 3600 * 1000 - 1);
        return vueloRepository.findBySalidaBetweenAndOrigenAndDestino(fecha, finDia, origen, destino);
    }

    // Devolver todos los vuelos
    public List<Vuelo> getVuelos(){
        return vueloRepository.findAll();
    }

}
