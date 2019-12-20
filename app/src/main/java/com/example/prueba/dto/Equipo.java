package com.example.prueba.dto;

import java.io.Serializable;

public class Equipo implements Serializable {
    private int serie;
    private String marca;
    private String descripcion;

    public Equipo() {
    }


    public Equipo(int serie, String marca, String descripcion) {
        this.serie = serie;
        this.marca = marca;
        this.descripcion = descripcion;
    }

    public Equipo(String marca, String descripcion) {
        this.marca = marca;
        this.descripcion = descripcion;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return (serie + " || " + marca + " || " + descripcion);
    }
}
