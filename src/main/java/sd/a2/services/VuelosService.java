package sd.a2.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aerolinea;
import sd.a2.model.Aeropuerto;
import sd.a2.model.Vuelo;
import sd.a2.repositories.AeropuertoRepository;
import sd.a2.repositories.VueloRepository;
import sd.a2.repositories.AerolineaRepository;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class VuelosService {

    @Autowired
    private VueloRepository vueloRepository;
    @Autowired
    private AerolineaRepository aerolineaRepository;
    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    public VuelosService(){

    }

    public Vuelo getVuelo(String codigo){
        return vueloRepository.findByCodigo(codigo);
    }

    public List<Vuelo> getVuelosFecha(Date fecha){
        Date finDia = new Date(fecha.getTime() + 24 * 3600 * 1000 - 1);
        return vueloRepository.findBySalidaBetween(fecha, finDia);
    }

    public List<Vuelo> getVuelosFechaOrigenDestino(Date fecha, Aeropuerto origen, Aeropuerto destino){
        Date finDia = new Date(fecha.getTime() + 24 * 3600 * 1000 - 1);
        return vueloRepository.findBySalidaBetweenAndOrigenAndDestino(fecha, finDia, origen, destino);
    }


    public List<Vuelo> getVuelos(){
        return vueloRepository.findAll();
    }



}
