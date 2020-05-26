package sd.a2.model;

import java.util.Date;

public class Vuelo {
    private String codigo;
    private Date salida;
    private int duracion;  // en minutos
    private int precio;  // euros

    public Vuelo(){}

    public Vuelo(String codigo, Date salida, int duracion, int precio) {
        this.codigo = codigo;
        this.salida = salida;
        this.duracion = duracion;
        this.precio = precio;
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
