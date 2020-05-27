package sd.a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aeropuerto;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.repositories.AeropuertoRepository;
import sd.a2.repositories.VueloRepository;

import java.util.List;

@Component
public class AeropuertoService {
    @Autowired
    private VueloRepository vueloRepository;
    private AerolineaRepository aerolineaRepository;
    private AeropuertoRepository aeropuertoRepository;

    public AeropuertoService(){}

    public Aeropuerto getAeropuerto(String codigo){return aeropuertoRepository.findByCodigo(codigo);}

    public List<Aeropuerto> getAeropuertos(){return aeropuertoRepository.findAll();}
}
