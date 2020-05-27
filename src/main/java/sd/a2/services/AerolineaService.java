package sd.a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aerolinea;
import sd.a2.model.Vuelo;
import sd.a2.repositories.AerolineaRepository;
import sd.a2.repositories.VueloRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AerolineaService {
    @Autowired
    private AerolineaRepository aerolineaRepository;

    //private Map<String, Aerolinea> aerolineaMap = new HashMap<String, Aerolinea>();
    public AerolineaService(){
        //crearDatos();
    }

    public Aerolinea getAerolinea(String codigo){
        return aerolineaRepository.findByCodigo(codigo);
    }

    public List<Aerolinea> getAerolineas(){
        return aerolineaRepository.findAll();
    }

    /*public void addAerolinea(Aerolinea aerolinea){
        this.aerolineaMap.put(aerolinea.getCodigo(), aerolinea);
    }

    public void modifyAerolinea(Aerolinea aerolinea){
        this.aerolineaMap.remove(aerolinea.getCodigo());
        this.aerolineaMap.put(aerolinea.getCodigo(), aerolinea);
    }*/

    private void crearDatos(){
        Aerolinea iberia = new Aerolinea("Iberia", "IB", "www.iberia.com", "901111500", 0);
        Aerolinea ryanair = new Aerolinea("Ryanair", "FR", "www.ryanair.com", "918294840", 0);
        Aerolinea british = new Aerolinea("British Airways", "BA", "www.ba.com", "912754779", 0);
        Aerolinea aireuropa = new Aerolinea("Air Europa", "UX", "www.aireuropa.com", "911401501", 0);
        aerolineaRepository.save(iberia);
        aerolineaRepository.save(ryanair);
        aerolineaRepository.save(british);
        aerolineaRepository.save(aireuropa);
    }
}
