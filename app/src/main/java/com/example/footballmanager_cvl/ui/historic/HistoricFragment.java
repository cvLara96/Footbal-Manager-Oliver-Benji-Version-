package com.example.footballmanager_cvl.ui.historic;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.footballmanager_cvl.R;
import com.example.footballmanager_cvl.databinding.FragmentFechaBinding;
import com.example.footballmanager_cvl.databinding.FragmentHistoricBinding;
import com.example.footballmanager_cvl.databinding.FragmentSaveBinding;
import com.example.footballmanager_cvl.ui.home.Jugador;
import com.example.footballmanager_cvl.ui.home.MyAdapter;
import com.example.footballmanager_cvl.ui.save.SaveViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoricFragment extends Fragment {

    private HistoricViewModel mViewModel;

    private FragmentHistoricBinding binding;
    private DatabaseReference mDatabase;
    ArrayList<String>fechas;
    Spinner spinnerFecha;
    RecyclerView recyclerView;
    MyAdapterHistoric adapterHistoric;
    Context contextHistoric;

    public static HistoricFragment newInstance() {
        return new HistoricFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =
                new ViewModelProvider(this).get(HistoricViewModel.class);

        binding = FragmentHistoricBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinnerFecha = binding.spinner;
        recyclerView = binding.recyclerHistoric;
        contextHistoric = getContext();

        fechas = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        obtenerFechasFirebase();

        recyclerView.setHasFixedSize(true);
        //.setHasFixedSieze(true) se utiliza en un RecyclerView en Android y tiene un impacto en
        //el rendimiento al establecer si el tamaño del RecyclerView es fijo
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //.setLayoutManager(new LinearLayoutManager(this)); establece un tipo de listado lineal
        //de arriba a abajo


        spinnerFecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obtenerJugadoresFirebase(parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoricViewModel.class);
        // TODO: Use the ViewModel
    }

    //Metodo que obtiene las fechas y las mete al spinner de fechas:
    public void obtenerFechasFirebase(){

        //Obtenemos las fechas
        mDatabase.child("Alineaciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    String fecha = ds.getKey();
                    fechas.add(fecha);
                }
                for(String s : fechas){
                    System.out.println(s.toString());
                }

                ArrayAdapter<String> adapterSpinnerFecha;
                adapterSpinnerFecha = new ArrayAdapter<>(contextHistoric, android.R.layout.simple_spinner_item, fechas);
                spinnerFecha.setAdapter(adapterSpinnerFecha);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Metodo para obtener a los jugadores dependiendo de la fecha:
    public void obtenerJugadoresFirebase(String fecha){

        ArrayList<Jugador>jugadores = new ArrayList<>();

        //Obtenemos las fechas
        mDatabase.child("Alineaciones").child(fecha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    boolean convocado = Boolean.parseBoolean(ds.child("convocado").getValue().toString());
                    Jugador jugador = new Jugador(
                            ds.child("nombre").getValue().toString(),
                            ds.child("posicion").getValue().toString(),
                            ds.child("fotoJugador").getValue().toString(),
                            convocado
                    );
                    jugadores.add(jugador);
                }

                for(Jugador j : jugadores){
                    System.out.println(j.getNombre());
                }
                //REFERENCIAMOS EL ADAPTADOR (recibe como parametros la lista de elementos y el contexto (de donde viene))
                adapterHistoric = new MyAdapterHistoric(jugadores,contextHistoric);
                //ASIGNAMOS EL ADAPTADOR
                recyclerView.setAdapter(adapterHistoric);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}