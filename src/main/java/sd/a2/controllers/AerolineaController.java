package sd.a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sd.a2.model.Aerolinea;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.services.AerolineaService;
import javax.annotation.PostConstruct;
import java.util.List;


@RestController
// Controller para gestionar el servicio web y devolver datos sobre las aerolineas
// Solo se han hecho metodos que se utilizan / necesitan en la pagina. Los que no se necesitan
// no se han implementado (por ejemplo, devolver una aerolinea por su nombre)
public class AerolineaController {
    @Autowired
    // Servicio que gestiona el repositorio
    private AerolineaService aerolineaService;

    @Autowired
    // Repositorio de aerolineas. Se declara aqui solo para inicializarlo con init()
    private AerolineaRepository aerolineaRepository;

    @PostConstruct
    // Metodo para introducir vuelos en la base de datos
    public void init(){
        Aerolinea iberia = new Aerolinea("Iberia", "IB", "http://www.iberia.com", "901111500", 4.5);
        Aerolinea ryanair = new Aerolinea("Ryanair", "FR", "http://www.ryanair.com", "918294840", 2.7);
        Aerolinea british = new Aerolinea("British Airways", "BA", "http://www.ba.com", "912754779", 4.2);
        Aerolinea aireuropa = new Aerolinea("Air Europa", "UX", "http://www.aireuropa.com", "911401501", 3.9);
        Aerolinea airfrance = new Aerolinea("Air France", "AF", "https://www.airfrance.es/", "913753335", 2.5);
        Aerolinea lufthansa = new Aerolinea("Lufthansa", "LH", "https://www.lufthansa.com", "902883882", 5);
        aerolineaRepository.save(iberia);
        aerolineaRepository.save(ryanair);
        aerolineaRepository.save(british);
        aerolineaRepository.save(aireuropa);
        aerolineaRepository.save(airfrance);
        aerolineaRepository.save(lufthansa);
    }

    // Devolver todas las aerolineas
    @RequestMapping(value="/aerolineas/", method = RequestMethod.GET)
    public List<Aerolinea> getAerolineas(){return aerolineaService.getAerolineas();}

}
