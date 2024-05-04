package com.example.gestorempleadosaritzmendive;

import java.time.LocalDate;

public class Trabajador {
    private String nombre;
    private String puesto;
    private int salario;
    private LocalDate fecha;

    public Trabajador(String nombre, String puesto, int salario, LocalDate fecha){
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fecha = fecha;
    }
}
