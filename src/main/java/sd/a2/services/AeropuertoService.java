package sd.a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aeropuerto;
import sd.a2.repositories.AeropuertoRepository;
import java.util.List;

@Component
// Clase auxiliar para gestionar el respositorio de aeropuertos
public class AeropuertoService {
    @Autowired
    // repositorio de aeropuertos
    private AeropuertoRepository aeropuertoRepository;

    public AeropuertoService(){}

    // Devolver todos los aeropuertos
    public List<Aeropuerto> getAeropuertos(){return aeropuertoRepository.findAll();}

}
