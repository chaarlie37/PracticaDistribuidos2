package sd.a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.a2.model.Aerolinea;
import sd.a2.model.Vuelo;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.repositories.VueloRepository;
import sd.a2.services.AerolineaService;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@RestController
public class AerolineaController {
    @Autowired
    private AerolineaService aerolineaService;

    @Autowired
    private AerolineaRepository aerolineaRepository;

    @PostConstruct
    public void init(){
        Aerolinea iberia = new Aerolinea("Iberia", "IB", "www.iberia.com", "901111500", 0);
        Aerolinea ryanair = new Aerolinea("Ryanair", "FR", "www.ryanair.com", "918294840", 0);
        Aerolinea british = new Aerolinea("British Airways", "BA", "www.ba.com", "912754779", 0);
        Aerolinea aireuropa = new Aerolinea("Air Europa", "UX", "www.aireuropa.com", "911401501", 0);
        aerolineaRepository.save(iberia);
        aerolineaRepository.save(ryanair);
        aerolineaRepository.save(british);
        aerolineaRepository.save(aireuropa);
    }

    @RequestMapping(value="/aerolinea/{codigo}", method = RequestMethod.GET)
    public Aerolinea getAerolinea(@PathVariable("codigo")String codigo){
        return aerolineaService.getAerolinea(codigo);
    }

    @RequestMapping(value="/aerolineas", method = RequestMethod.GET)
    public List<Aerolinea> getAerolineas(){return aerolineaService.getAerolineas();}

    /*
    @RequestMapping(value="/aerolinea", method = RequestMethod.POST)

    public ResponseEntity<Boolean> addAerolinea(@RequestBody Aerolinea aerolinea){
        aerolineaService.addAerolinea(aerolinea);
        return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }*/


}
