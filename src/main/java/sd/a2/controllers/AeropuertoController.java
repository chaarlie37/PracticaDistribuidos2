package sd.a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sd.a2.model.Aeropuerto;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.repositories.AeropuertoRepository;
import sd.a2.repositories.VueloRepository;
import sd.a2.services.AeropuertoService;

import javax.annotation.PostConstruct;
import java.util.List;


@RestController
// Controller para gestionar el servicio web y devolver datos sobre las aerolineas
public class AeropuertoController {
    @Autowired
    // Servicio que gestiona el repositorio
    private AeropuertoService aeropuertoService;

    @Autowired
    // Repositorio de aeropuertos. Se declara aqui solo para inicializarlo con init()
    private AeropuertoRepository aeropuertoRepository;

    @PostConstruct
    // Metodo para introducir vuelos en la base de datos
    public void init(){
        Aeropuerto madrid = new Aeropuerto("LEMD", "Madrid (Adolfo Suárez - Madrid Barajas)");
        Aeropuerto roma1 = new Aeropuerto("LIRF", "Roma (Fiumicino)");
        Aeropuerto roma2 = new Aeropuerto("LIRA", "Roma (Ciampino)");
        Aeropuerto palma = new Aeropuerto("LEPA", "Palma de Mallorca");
        Aeropuerto berlin = new Aeropuerto("EDDB", "Berlin (Schönefeld)");
        Aeropuerto ny = new Aeropuerto("KJFK", "Nueva York (John F. Kennedy)");
        Aeropuerto bcn = new Aeropuerto("LEBL", "Barcelona (El Prat)");
        Aeropuerto paris = new Aeropuerto("LFPO", "París (Orly)");
        aeropuertoRepository.save(madrid);
        aeropuertoRepository.save(roma1);
        aeropuertoRepository.save(roma2);
        aeropuertoRepository.save(palma);
        aeropuertoRepository.save(berlin);
        aeropuertoRepository.save(ny);
        aeropuertoRepository.save(bcn);
        aeropuertoRepository.save(paris);
    }

    // Devolver todos los aeropuertos
    @RequestMapping(value = "/aeropuertos", method = RequestMethod.GET)
    public List<Aeropuerto> getAeropuertos(){
        return aeropuertoService.getAeropuertos();
    }

    // Devolver un aeropuerto dado su nombre
    @RequestMapping(value= "/aeropuerto/{nombre}", method = RequestMethod.GET)
    public Aeropuerto getAeropuerto(@PathVariable("nombre") String nombre){
        return aeropuertoService.getAeropuerto(nombre);
    }




}
