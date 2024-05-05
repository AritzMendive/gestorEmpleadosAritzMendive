package Modelo;

import java.time.LocalDate;

public class Trabajador {
    private String nombre;
    private String puesto;
    private int salario;
    private LocalDate fecha;
    private int id;

    public Trabajador(String nombre, String puesto, int salario, LocalDate fech, int id){
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fecha = fecha;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String nuevoPuesto) {
        this.puesto = nuevoPuesto;
    }
}

