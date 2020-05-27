package sd.a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sd.a2.model.Aerolinea;
import sd.a2.model.Aeropuerto;
import sd.a2.model.Vuelo;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.repositories.AeropuertoRepository;
import sd.a2.repositories.VueloRepository;
import sd.a2.services.AeropuertoService;
import sd.a2.services.VuelosService;
import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class VueloController {

    @Autowired
    private VuelosService vuelosService;

    @Autowired
    private AeropuertoService aeropuertoService;

    @Autowired
    private AerolineaRepository aerolineaRepository;

    @Autowired
    private VueloRepository vueloRepository;

    @Autowired
    private AeropuertoRepository aeropuertoRepository;


    @PostConstruct
    public void init(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Aerolinea iberia = new Aerolinea("Iberia", "IB", "www.iberia.com", "901111500", 0);
        Aerolinea ryanair = new Aerolinea("Ryanair", "FR", "www.ryanair.com", "918294840", 0);
        Aerolinea british = new Aerolinea("British Airways", "BA", "www.ba.com", "912754779", 0);
        Aerolinea aireuropa = new Aerolinea("Air Europa", "UX", "www.aireuropa.com", "911401501", 0);
        aerolineaRepository.save(iberia);
        aerolineaRepository.save(ryanair);
        aerolineaRepository.save(british);
        aerolineaRepository.save(aireuropa);
        Aeropuerto madrid = new Aeropuerto("LEMD", "Madrid (Adolfo Suárez - Madrid Barajas)", "Madrid");
        Aeropuerto roma1 = new Aeropuerto("LIRF", "Roma (Fiumicino)", "Roma");
        Aeropuerto roma2 = new Aeropuerto("LIRA", "Roma (Ciampino)", "Roma");
        Aeropuerto palma = new Aeropuerto("LEPA", "Palma de Mallorca", "Palma de Mallorca");
        Aeropuerto berlin = new Aeropuerto("EDDB", "Berlin (Schönefeld)", "Berlín");
        Aeropuerto ny = new Aeropuerto("KJFK", "Nueva York (John F. Kennedy)", "Nueva York");
        aeropuertoRepository.save(madrid);
        aeropuertoRepository.save(roma1);
        aeropuertoRepository.save(roma2);
        aeropuertoRepository.save(palma);
        aeropuertoRepository.save(berlin);
        aeropuertoRepository.save(ny);
        try{
            Vuelo v1 = new Vuelo("IB0001", simpleDateFormat.parse("01-06-2020 12:15"), 120, 150, iberia, madrid, berlin);
            Vuelo v2 = new Vuelo("IB0002", simpleDateFormat.parse("02-06-2020 07:35"), 90, 120, iberia, madrid, roma1);
            Vuelo v3 = new Vuelo("FR0001", simpleDateFormat.parse("01-06-2020 09:30"), 95, 50, ryanair, roma2, madrid);
            Vuelo v4 = new Vuelo("BA0001", simpleDateFormat.parse("02-06-2020 17:00"), 450, 500, british, madrid, ny);
            Vuelo v5 = new Vuelo("UX0001", simpleDateFormat.parse("01-06-2020 23:30"), 45, 75, aireuropa, madrid, palma);
            Vuelo v6 = new Vuelo("UX0002", simpleDateFormat.parse("01-06-2020 23:30"), 45, 75, aireuropa, madrid, berlin);
            vueloRepository.save(v1);
            vueloRepository.save(v2);
            vueloRepository.save(v3);
            vueloRepository.save(v4);
            vueloRepository.save(v5);
            vueloRepository.save(v6);
        }catch (ParseException e){}
    }

    @RequestMapping(value = "/vuelos", method = RequestMethod.GET)
    public List<Vuelo> getVuelos(){
        return vuelosService.getVuelos();
    }

    @RequestMapping(value = "/vuelos/{fecha}", method = RequestMethod.GET)
    public List<Vuelo> getVuelosFecha(@PathVariable("fecha") String fecha){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("FECHA");
        try{
            System.out.println(simpleDateFormat.parse(fecha));
            return vuelosService.getVuelosFecha(simpleDateFormat.parse(fecha));
        } catch (ParseException e){
            return null;
        }
    }

    @RequestMapping(value = "/vuelos/{fecha}/{origen}-{destino}", method = RequestMethod.GET)
    public List<Vuelo> getVuelosFecha(@PathVariable("fecha") String fecha, @PathVariable("origen") String origen, @PathVariable("destino") String destino){
        Aeropuerto aeropuertoOrigen = aeropuertoService.getAeropuerto(origen);
        Aeropuerto aeropuertoDestino = aeropuertoService.getAeropuerto(destino);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            System.out.println(simpleDateFormat.parse(fecha));
            return vuelosService.getVuelosFechaOrigenDestino(simpleDateFormat.parse(fecha), aeropuertoOrigen, aeropuertoDestino);
        } catch (ParseException e){
            return null;
        }
    }

    @RequestMapping(value = "/vuelo/{codigo}")
    public Vuelo getVuelo(@PathVariable("codigo") String codigo){
        return vuelosService.getVuelo(codigo);
    }


}
