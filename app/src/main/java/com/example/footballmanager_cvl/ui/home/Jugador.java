package com.example.footballmanager_cvl.ui.home;

import java.util.ArrayList;

public class Jugador {

    String nombre;
    String posicion;
    String fotoJugador;
    boolean convocado;

    //Constructor
    public Jugador(String nombre, String posicion, String fotoJugador, boolean convocado) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.fotoJugador = fotoJugador;
        this.convocado = convocado;

    }
    public Jugador(String nombre, String posicion, String fotoJugador) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.fotoJugador = fotoJugador;

    }


    //Getter y setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getFotoJugador() {
        return fotoJugador;
    }

    public void setFotoJugador(String fotoJugador) {
        this.fotoJugador = fotoJugador;
    }

    public boolean isConvocado() {
        return convocado;
    }

    public void setConvocado(boolean convocado) {
        this.convocado = convocado;
    }
}
