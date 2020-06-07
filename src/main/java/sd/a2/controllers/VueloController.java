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
        Aerolinea iberia = new Aerolinea("Iberia", "IB", "http://www.iberia.com", "901111500", 4.5);
        Aerolinea ryanair = new Aerolinea("Ryanair", "FR", "http://www.ryanair.com", "918294840", 2.7);
        Aerolinea british = new Aerolinea("British Airways", "BA", "http://www.ba.com", "912754779", 4.2);
        Aerolinea aireuropa = new Aerolinea("Air Europa", "UX", "http://www.aireuropa.com", "911401501", 3.9);
        Aerolinea airfrance = new Aerolinea("Air France", "FA", "https://www.airfrance.es/", "913753335", 2.5);
        Aerolinea lufthansa = new Aerolinea("Lufthansa", "LH", "https://www.lufthansa.com", "902883882", 5);
        aerolineaRepository.save(iberia);
        aerolineaRepository.save(ryanair);
        aerolineaRepository.save(british);
        aerolineaRepository.save(aireuropa);
        aerolineaRepository.save(airfrance);
        aerolineaRepository.save(lufthansa);
        Aeropuerto madrid = new Aeropuerto("LEMD", "Madrid (Adolfo Suárez - Madrid Barajas)", "Madrid");
        Aeropuerto roma1 = new Aeropuerto("LIRF", "Roma (Fiumicino)", "Roma");
        Aeropuerto roma2 = new Aeropuerto("LIRA", "Roma (Ciampino)", "Roma");
        Aeropuerto palma = new Aeropuerto("LEPA", "Palma de Mallorca", "Palma de Mallorca");
        Aeropuerto berlin = new Aeropuerto("EDDB", "Berlin (Schönefeld)", "Berlín");
        Aeropuerto ny = new Aeropuerto("KJFK", "Nueva York (John F. Kennedy)", "Nueva York");
        Aeropuerto bcn = new Aeropuerto("LEBL", "Barcelona (El Prat)", "Barcelona");
        Aeropuerto paris = new Aeropuerto("LFPO", "París (Orly)", "París");
        aeropuertoRepository.save(madrid);
        aeropuertoRepository.save(roma1);
        aeropuertoRepository.save(roma2);
        aeropuertoRepository.save(palma);
        aeropuertoRepository.save(berlin);
        aeropuertoRepository.save(ny);
        aeropuertoRepository.save(bcn);
        aeropuertoRepository.save(paris);
        try{
            Vuelo v1 = new Vuelo("IB0001", simpleDateFormat.parse("31-05-2020 12:15"), 120, 150, iberia, madrid, berlin);
            Vuelo v2 = new Vuelo("IB0002", simpleDateFormat.parse("31-05-2020 07:35"), 90, 120, iberia, madrid, roma1);
            Vuelo v3 = new Vuelo("FR0001", simpleDateFormat.parse("02-06-2020 09:30"), 95, 50, ryanair, roma1, madrid);
            Vuelo v4 = new Vuelo("BA0001", simpleDateFormat.parse("02-06-2020 17:00"), 450, 500, british, madrid, ny);
            Vuelo v5 = new Vuelo("UX0001", simpleDateFormat.parse("01-06-2020 23:30"), 45, 75, aireuropa, madrid, palma);
            Vuelo v6 = new Vuelo("UX0002", simpleDateFormat.parse("31-05-2020 23:30"), 125, 75, aireuropa, madrid, berlin);
            Vuelo v7 = new Vuelo("UX0003", simpleDateFormat.parse("02-06-2020 12:15"), 120, 140, aireuropa, berlin, madrid);
            Vuelo v8 = new Vuelo("IB0003", simpleDateFormat.parse("31-05-2020 11:45"), 55, 80, iberia, madrid, bcn);
            Vuelo v9 = new Vuelo("IB0004", simpleDateFormat.parse("31-05-2020 12:30"), 55, 100, iberia, madrid, bcn);
            Vuelo v10 = new Vuelo("IB0005", simpleDateFormat.parse("31-05-2020 13:55"), 55, 95, iberia, madrid, bcn);
            Vuelo v11 = new Vuelo("IB0006", simpleDateFormat.parse("31-05-2020 15:00"), 55, 60, iberia, bcn, madrid);
            Vuelo v12 = new Vuelo("IB0007", simpleDateFormat.parse("31-05-2020 18:10"), 55, 75, iberia, madrid, bcn);
            Vuelo v13 = new Vuelo("IB0008", simpleDateFormat.parse("31-05-2020 22:25"), 55, 50, iberia, bcn, madrid);
            Vuelo v14 = new Vuelo("AF0001", simpleDateFormat.parse("31-05-2020 07:25"), 100, 120, airfrance, madrid, paris);
            Vuelo v15 = new Vuelo("AF0002", simpleDateFormat.parse("02-06-2020 07:25"), 110, 130, airfrance, paris, madrid);
            Vuelo v16 = new Vuelo("LF0001", simpleDateFormat.parse("31-05-2020 16:15"), 120, 140, lufthansa, madrid, berlin);
            Vuelo v17 = new Vuelo("LF0002", simpleDateFormat.parse("02-06-2020 17:30"), 115, 100, lufthansa, berlin, madrid);

            vueloRepository.save(v1);
            vueloRepository.save(v2);
            vueloRepository.save(v3);
            vueloRepository.save(v4);
            vueloRepository.save(v5);
            vueloRepository.save(v6);
            vueloRepository.save(v7);
            vueloRepository.save(v8);
            vueloRepository.save(v9);
            vueloRepository.save(v10);
            vueloRepository.save(v11);
            vueloRepository.save(v12);
            vueloRepository.save(v13);
            vueloRepository.save(v14);
            vueloRepository.save(v15);
            vueloRepository.save(v16);
            vueloRepository.save(v17);
        }catch (ParseException e){}
    }

    @RequestMapping(value = "/vuelos", method = RequestMethod.GET)
    public List<Vuelo> getVuelos(){
        return vuelosService.getVuelos();
    }

    @RequestMapping(value = "/vuelos/{fecha}", method = RequestMethod.GET)
    public List<Vuelo> getVuelosFecha(@PathVariable("fecha") String fecha){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("FECHA");
        try{
            System.out.println(simpleDateFormat.parse(fecha));
            return vuelosService.getVuelosFecha(simpleDateFormat.parse(fecha));
        } catch (ParseException e){
            return null;
        }
    }

    @RequestMapping(value = "/vuelos/{fecha}/{origen}/{destino}", method = RequestMethod.GET)
    public List<Vuelo> getVuelosFecha(@PathVariable("fecha") String fecha, @PathVariable("origen") String origen, @PathVariable("destino") String destino){
        Aeropuerto aeropuertoOrigen = aeropuertoService.getAeropuerto(origen);
        Aeropuerto aeropuertoDestino = aeropuertoService.getAeropuerto(destino);
        System.out.println(origen);
        System.out.println(destino);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
