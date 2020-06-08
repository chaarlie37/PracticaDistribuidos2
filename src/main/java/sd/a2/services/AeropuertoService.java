package sd.a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aeropuerto;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.repositories.AeropuertoRepository;
import sd.a2.repositories.VueloRepository;
import java.util.List;

@Component
// Clase auxiliar para gestionar el respositorio de aeropuertos
public class AeropuertoService {
    @Autowired
    // repositorio de aeropuertos
    private AeropuertoRepository aeropuertoRepository;

    public AeropuertoService(){}

    // Devolver un aeropuerto dado su nombre
    public Aeropuerto getAeropuerto(String nombre){return aeropuertoRepository.findByNombre(nombre);}

    // Devolver todos los aeropuertos
    public List<Aeropuerto> getAeropuertos(){return aeropuertoRepository.findAll();}

}
