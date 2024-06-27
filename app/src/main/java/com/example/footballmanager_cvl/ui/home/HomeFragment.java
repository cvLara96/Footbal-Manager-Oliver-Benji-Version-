package com.example.footballmanager_cvl.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballmanager_cvl.R;
import com.example.footballmanager_cvl.SaveArray;
import com.example.footballmanager_cvl.databinding.FragmentHomeBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //Equipo

    //Creamos el array de jugadores
    public static ArrayList<Jugador> jugadores = new ArrayList<>();

    //Creamos el array static de convocados
    public static ArrayList<Jugador>convocados = new ArrayList<>();

    //Crearemos el adaptador:
    MyAdapter adaptador;
    //Creamos el recyclerView
    RecyclerView recyclerView;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Referenciamos el relativeLayout, para ello creamos un view v y lo inflamos
        /*con la vista que contiene el lista_jugadores.xml
        View vistaLista = inflater.inflate(R.layout.list_jugadores, container, false);
        relativeLayout = vistaLista.findViewById(R.id.relativePulsable);
        imageFav = relativeLayout.findViewById(R.id.imageFav);*/

        //Referenciamos los arraylist

        System.out.println("Fragmento equipo");
        if(SaveArray.getConvocadosGuardados()!=null){
            convocados = SaveArray.getConvocadosGuardados();
        }

        //Insertamos los jugadores, en el caso de que jugadores este vacio:
        if(jugadores.isEmpty()){
            addJugadores();
        }

        //REFERENCIAMOS EL ADAPTADOR (recibe como parametros la lista de elementos y el contexto (de donde viene))
        adaptador = new MyAdapter(jugadores, getContext(), this);

        //Referenciamos el recycler
        recyclerView = binding.recyclerJugadores;

        recyclerView.setHasFixedSize(true);
        //.setHasFixedSieze(true) se utiliza en un RecyclerView en Android y tiene un impacto en
        //el rendimiento al establecer si el tamaño del RecyclerView es fijo
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //.setLayoutManager(new LinearLayoutManager(this)); establece un tipo de listado lineal
        //de arriba a abajo

        //ASIGNAMOS EL ADAPTADOR
        recyclerView.setAdapter(adaptador);


        final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    //CREAMOS UN METODO QUE LLAMAREMOS DESDE EL ADAPTADOR CUANDO SE PULSE
    //SOBRE UN RELATIVE LAYOUT:
    public void finalizar(String jugadorElegido, String posicionJugador, String fotoJugador){

        Jugador nuevoJugador = null;

        if(convocados != null && convocados.isEmpty()){
            nuevoJugador = new Jugador(jugadorElegido,posicionJugador, fotoJugador);
            convocados.add(nuevoJugador);
            /*
            * for(Jugador j : convocados){
                System.out.println(j.getNombre());
            }*/
        }else{
            for(Jugador jug : convocados){

                if(jug.getNombre().equals(jugadorElegido)&&jug.getPosicion().equals(posicionJugador)){
                    nuevoJugador = jug;
                    break;
                }

            }

            if(nuevoJugador!=null){
                convocados.remove(nuevoJugador);
                for(Jugador j : convocados){
                    System.out.println(j.getNombre());
                }
            }else{
                nuevoJugador = new Jugador(jugadorElegido,posicionJugador, fotoJugador);
                convocados.add(nuevoJugador);
                for(Jugador j : convocados){
                    System.out.println(j.getNombre());
                }
            }


        }

    }

    public void actualizarInfo (String jugadorElegido, String posicionJugador) {

        Jugador jugadorActualizado = null;

        for (Jugador j : jugadores) {

            if (j.getNombre().equals(jugadorElegido) && j.getPosicion().equals(posicionJugador)) {
                jugadorActualizado = j;
                break;
            }
        }
            if (jugadorActualizado.isConvocado()) {

                jugadorActualizado.setConvocado(false);

            } else {
                jugadorActualizado.setConvocado(true);
                /*for(Jugador jug : jugadores){
                    System.out.println(jug.getNombre() + " - "+jug.isConvocado());
                }*/
            }
    }

    public void addJugadores(){
        //Insertamos los jugadores en el list de jugadores
        jugadores.add(new Jugador("Oliver Atom","Centrocampista", "FotosJugadores/oliver.png",false));
        jugadores.add(new Jugador("Benji Price","Portero", "FotosJugadores/benji.png",false));
        jugadores.add(new Jugador("Tom Baker","Centrocampista","FotosJugadores/TomBaker.png",false));
        jugadores.add(new Jugador("Mark Lenders","Delantero","FotosJugadores/MarkLenders.png",false));
        jugadores.add(new Jugador("Ed Warner","Portero","FotosJugadores/EdWarner.png",false));
        jugadores.add(new Jugador("Phillip Callahan","Defensa","FotosJugadores/PhillipCallahan.png",false));
        jugadores.add(new Jugador("Julian Ross","Centrocampista","FotosJugadores/JulianRoss.png",false));
        jugadores.add(new Jugador("James Derrick","Lateral izquierdo","FotosJugadores/JamesDerrick.png",false));
        jugadores.add(new Jugador("Jason Derrick","Lateral derecho","FotosJugadores/JasonDerrick.png",false));
        jugadores.add(new Jugador("Dany Mellow","Delantero","FotosJugadores/DannyMellow.png",false));
        jugadores.add(new Jugador("Paul Diamond","Defensa","FotosJugadores/PaulDiamond.png",false));
        jugadores.add(new Jugador("Bruce Harper","Defensa","FotosJugadores/BruceHarper.png",false));
        jugadores.add(new Jugador("Patrick Everett","Delantero","FotosJugadores/PatrickEverett.png",false));
        jugadores.add(new Jugador("Eddie Carter","Centrocampista","FotosJugadores/TedCarter.png",false));
        jugadores.add(new Jugador("Alan Crockett","Portero","FotosJugadores/AllanCrocker.png",false));
        jugadores.add(new Jugador("Clifford Yuma","Defensa","FotosJugadores/CliffordYuma.png",false));
        jugadores.add(new Jugador("Jack Morris ","Defensa","FotosJugadores/JackMorris.png",false));
        jugadores.add(new Jugador("Johnny Mason","Lateral derecho","FotosJugadores/Masonn.png",false));
        jugadores.add(new Jugador("Teo Sellers","Portero","FotosJugadores/teo.png",false));
        jugadores.add(new Jugador("Ralph Peterson","Lateral izquierdo","FotosJugadores/RalphPatterson.png",false));
    }

    //Metodo para limpiar convocados cuando se pulse el boton borrar convocados y resetear el valor del
    //atributo convocado de los jugadores a false
    public static void reset(){

        convocados.clear();

        for(Jugador j : jugadores){
            j.setConvocado(false);
        }


    }



    //Metodo onPause para que mantenga los cambios del array de convocados y de jugadores al cambiar de fragmento:

    @Override
    public void onPause() {
        super.onPause();
        SaveArray.save(jugadores, convocados);

    }

    //Metodo onResume
    @Override
    public void onResume() {
        super.onResume();
        if(SaveArray.getJugadoresGuardados()!=null){
            jugadores = SaveArray.getJugadoresGuardados();
            for(Jugador jug : jugadores){
                System.out.println(jug.getNombre() + " " + jug.isConvocado());
            }
        }else if(SaveArray.getConvocadosGuardados()!=null){
            convocados = SaveArray.getConvocadosGuardados();
        }

        //Notificar los cambios al adaptador
        adaptador.notifyDataSetChanged();

    }


}