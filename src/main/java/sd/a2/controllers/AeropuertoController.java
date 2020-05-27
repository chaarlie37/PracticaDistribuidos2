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
public class AeropuertoController {
    @Autowired
    private AeropuertoService aeropuertoService;

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private AerolineaRepository aerolineaRepository;

    @Autowired
    private VueloRepository vueloRepository;



    @RequestMapping(value = "/aeropuertos", method = RequestMethod.GET)
    public List<Aeropuerto> getAeropuertos(){
        return aeropuertoService.getAeropuertos();
    }

    @RequestMapping(value = "/aeropuertos/{ciudad}", method = RequestMethod.GET)
    public List<Aeropuerto> getAeropuertosCiudad(@PathVariable("ciudad") String ciudad){
        return aeropuertoService.getAeropuertosCiudad(ciudad);
    }

    @RequestMapping(value= "/aeropuerto/{codigo}", method = RequestMethod.GET)
    public Aeropuerto getAeropuerto(@PathVariable("codigo") String codigo){
        return aeropuertoService.getAeropuerto(codigo);
    }




}
