package sd.a2.model;


import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Vuelo{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String codigo;
    private Date salida;
    private Date llegada;
    private int duracion;  // en minutos
    private int precio;  // euros
    @ManyToOne
    private Aerolinea aerolinea;
    @ManyToOne
    private Aeropuerto origen;
    @ManyToOne
    private Aeropuerto destino;

    public Vuelo(){}

    public Vuelo(String codigo, Date salida, int duracion, int precio, Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino) {
        this.codigo = codigo;
        this.salida = salida;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(salida);
        calendar.add(Calendar.MINUTE, duracion);
        this.llegada = calendar.getTime();
        this.duracion = duracion;
        this.precio = precio;
        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public Date getLlegada() {
        return llegada;
    }

    public void setLlegada(Date llegada) {
        this.llegada = llegada;
    }
}
