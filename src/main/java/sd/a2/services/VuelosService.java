package sd.a2.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aerolinea;
import sd.a2.model.Vuelo;
import sd.a2.repositories.VueloRepository;
import sd.a2.repositories.AerolineaRepository;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class VuelosService {

    @Autowired
    private VueloRepository vueloRepository;
    private AerolineaRepository aerolineaRepository;

    public VuelosService(){

    }

    public Vuelo getVuelo(String codigo){
        return vueloRepository.findByCodigo(codigo);
    }

    public List<Vuelo> getVuelosFecha(Date fecha){
        Date finDia = new Date(fecha.getTime() + 24 * 3600 * 1000 - 1);
        return vueloRepository.findAllBySalidaBetween(fecha, finDia);
    }


    public List<Vuelo> getVuelos(){
        return vueloRepository.findAll();
    }

    public void crearDatos(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Aerolinea iberia = new Aerolinea("Iberia", "IB", "www.iberia.com", "901111500", 0);
        Aerolinea ryanair = new Aerolinea("Ryanair", "FR", "www.ryanair.com", "918294840", 0);
        Aerolinea british = new Aerolinea("British Airways", "BA", "www.ba.com", "912754779", 0);
        Aerolinea aireuropa = new Aerolinea("Air Europa", "UX", "www.aireuropa.com", "911401501", 0);
        aerolineaRepository.save(iberia);
        aerolineaRepository.save(ryanair);
        aerolineaRepository.save(british);
        aerolineaRepository.save(aireuropa);
        try{
            Vuelo v1 = new Vuelo("IB0001", simpleDateFormat.parse("01-06-2020 12:15"), 120, 150, iberia);
            Vuelo v2 = new Vuelo("IB0002", simpleDateFormat.parse("02-06-2020 07:35"), 90, 120, iberia);
            Vuelo v3 = new Vuelo("FR0001", simpleDateFormat.parse("01-06-2020 09:30"), 300, 450, ryanair);
            Vuelo v4 = new Vuelo("BA0001", simpleDateFormat.parse("02-06-2020 17:00"), 135, 170, british);
            Vuelo v5 = new Vuelo("UX0001", simpleDateFormat.parse("01-06-2020 23:30"), 45, 75, aireuropa);
            vueloRepository.save(v1);
            vueloRepository.save(v2);
            vueloRepository.save(v3);
            vueloRepository.save(v4);
            vueloRepository.save(v5);
        }catch (ParseException e){}

    }


}
