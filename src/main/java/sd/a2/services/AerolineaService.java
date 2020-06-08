package sd.a2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.a2.model.Aerolinea;
import sd.a2.repositories.AerolineaRepository;
import java.util.List;

@Component
// Clase auxiliar para gestionar el respositorio de aerolineas
public class AerolineaService {
    @Autowired
    // Repositorio de aerolineas
    private AerolineaRepository aerolineaRepository;

    public AerolineaService(){
    }

    // Devolver todas las aerolineas
    public List<Aerolinea> getAerolineas(){
        return aerolineaRepository.findAll();
    }

}
