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
// Controller para gestionar el servicio web y devolver datos sobre las aerolineas
public class VueloController {

    @Autowired
    // Servicio que gestiona el repositorio
    private VuelosService vuelosService;

    @Autowired
    // Repositorio de vuelos. Se declara aqui solo para inicializarlo con init()
    private VueloRepository vueloRepository;

    @PostConstruct
    // Metodo para introducir vuelos en la base de datos
    public void init(){
        // Formato de la fecha y hora para crear los vuelos con fechas a partir de un string
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try{
            Vuelo v1 = new Vuelo("IB0001", simpleDateFormat.parse("31-05-2020 12:15"), 120, 150, "IB", "Madrid (Adolfo Suárez - Madrid Barajas)", "Berlin (Schönefeld)");
            Vuelo v2 = new Vuelo("IB0002", simpleDateFormat.parse("31-05-2020 07:35"), 90, 120, "IB", "Madrid (Adolfo Suárez - Madrid Barajas)", "Roma (Fiumicino)");
            Vuelo v3 = new Vuelo("FR0001", simpleDateFormat.parse("02-06-2020 09:30"), 95, 50, "FR", "Roma (Fiumicino)", "Madrid (Adolfo Suárez - Madrid Barajas)");
            Vuelo v4 = new Vuelo("BA0001", simpleDateFormat.parse("02-06-2020 17:00"), 450, 500, "BA", "Madrid (Adolfo Suárez - Madrid Barajas)", "Nueva York (John F. Kennedy)");
            Vuelo v5 = new Vuelo("UX0001", simpleDateFormat.parse("01-06-2020 23:30"), 45, 75, "UX", "Madrid (Adolfo Suárez - Madrid Barajas)", "Palma de Mallorca");
            Vuelo v6 = new Vuelo("UX0002", simpleDateFormat.parse("31-05-2020 23:30"), 125, 75, "UX", "Madrid (Adolfo Suárez - Madrid Barajas)", "Berlin (Schönefeld)");
            Vuelo v7 = new Vuelo("UX0003", simpleDateFormat.parse("02-06-2020 12:15"), 120, 140, "UX", "Berlin (Schönefeld)", "Madrid (Adolfo Suárez - Madrid Barajas)");
            Vuelo v8 = new Vuelo("IB0003", simpleDateFormat.parse("31-05-2020 11:45"), 55, 80, "IB", "Madrid (Adolfo Suárez - Madrid Barajas)", "Barcelona (El Prat)");
            Vuelo v9 = new Vuelo("IB0004", simpleDateFormat.parse("31-05-2020 12:30"), 55, 100, "IB", "Madrid (Adolfo Suárez - Madrid Barajas)", "Barcelona (El Prat)");
            Vuelo v10 = new Vuelo("FR0002", simpleDateFormat.parse("31-05-2020 13:55"), 50, 45, "FR", "Madrid (Adolfo Suárez - Madrid Barajas)", "Barcelona (El Prat)");
            Vuelo v11 = new Vuelo("IB0006", simpleDateFormat.parse("31-05-2020 15:00"), 55, 60, "IB", "Barcelona (El Prat)", "Madrid (Adolfo Suárez - Madrid Barajas)");
            Vuelo v12 = new Vuelo("IB0007", simpleDateFormat.parse("31-05-2020 18:10"), 55, 75, "IB", "Madrid (Adolfo Suárez - Madrid Barajas)", "Barcelona (El Prat)");
            Vuelo v13 = new Vuelo("UX0004", simpleDateFormat.parse("31-05-2020 22:25"), 55, 50, "UX", "Barcelona (El Prat)", "Madrid (Adolfo Suárez - Madrid Barajas)");
            Vuelo v14 = new Vuelo("AF0001", simpleDateFormat.parse("31-05-2020 07:25"), 100, 120, "AF", "Madrid (Adolfo Suárez - Madrid Barajas)", "París (Orly)");
            Vuelo v15 = new Vuelo("AF0002", simpleDateFormat.parse("02-06-2020 07:25"), 110, 130, "AF", "París (Orly)", "Madrid (Adolfo Suárez - Madrid Barajas)");
            Vuelo v16 = new Vuelo("LH0001", simpleDateFormat.parse("31-05-2020 16:15"), 120, 140, "LH", "Madrid (Adolfo Suárez - Madrid Barajas)", "Berlin (Schönefeld)");
            Vuelo v17 = new Vuelo("LH0002", simpleDateFormat.parse("02-06-2020 17:30"), 115, 100, "LH", "Berlin (Schönefeld)", "Madrid (Adolfo Suárez - Madrid Barajas)");
            Vuelo v18 = new Vuelo("IB0005", simpleDateFormat.parse("31-05-2020 07:30"), 65, 65, "IB", "Madrid (Adolfo Suárez - Madrid Barajas)", "Barcelona (El Prat)");
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
            vueloRepository.save(v18);
        }catch (ParseException e){
            System.err.println("Error al guardar los vuelos en la BBDD. Error al parsear la fecha.");
        }
    }

    // Devolver todos los vuelos
    @RequestMapping(value = "/vuelos", method = RequestMethod.GET)
    public List<Vuelo> getVuelos(){
        return vuelosService.getVuelos();
    }

    /*
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
    */

    // Devolver vuelos dada una fecha, origen y destino
    @RequestMapping(value = "/vuelos/{fecha}/{origen}/{destino}", method = RequestMethod.GET)
    public List<Vuelo> getVuelosFecha(@PathVariable("fecha") String fecha, @PathVariable("origen") String origen, @PathVariable("destino") String destino){
        // Convertir el formato de la fecha al formato dd-MM-yyyy
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try{
            return vuelosService.getVuelosFechaOrigenDestino(simpleDateFormat.parse(fecha), origen, destino);
        } catch (ParseException e){
            System.err.println("Error al hacer el parse de la fecha.");
            return null;
        }
    }

    // Devolver un vuelo dado su codigo
    @RequestMapping(value = "/vuelo/{codigo}")
    public Vuelo getVuelo(@PathVariable("codigo") String codigo){
        return vuelosService.getVuelo(codigo);
    }


}
