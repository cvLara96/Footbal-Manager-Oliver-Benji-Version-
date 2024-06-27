package com.example.footballmanager_cvl;

import com.example.footballmanager_cvl.ui.home.Jugador;

import java.util.ArrayList;

public class SaveArray {

    static ArrayList<Jugador>jugadoresGuardados;
    static ArrayList<Jugador>convocadosGuardados;


    public static ArrayList<Jugador> getJugadoresGuardados() {
        return jugadoresGuardados;
    }

    public void setJugadoresGuardados(ArrayList<Jugador> jugadoresGuardados) {
        this.jugadoresGuardados = jugadoresGuardados;
    }

    public static ArrayList<Jugador> getConvocadosGuardados() {
        return convocadosGuardados;
    }

    public void setConvocadosGuardados(ArrayList<Jugador> convocadosGuardados) {
        this.convocadosGuardados = convocadosGuardados;
    }

    public static void save (ArrayList<Jugador>jugadores, ArrayList<Jugador>convocados){
        jugadoresGuardados = jugadores;
        convocadosGuardados = convocados;

    }

}
