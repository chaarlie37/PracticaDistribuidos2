package sd.a2.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Vuelo{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String codigo;
    private Date salida;
    private int duracion;  // en minutos
    private int precio;  // euros
    @ManyToOne
    private Aerolinea aerolinea;

    public Vuelo(){}

    public Vuelo(String codigo, Date salida, int duracion, int precio, Aerolinea aerolinea) {
        this.codigo = codigo;
        this.salida = salida;
        this.duracion = duracion;
        this.precio = precio;
        this.aerolinea = aerolinea;
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
}
