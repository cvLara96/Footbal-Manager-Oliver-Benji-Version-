package com.example.footballmanager_cvl.ui.save;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footballmanager_cvl.R;
import com.example.footballmanager_cvl.databinding.FragmentFechaBinding;
import com.example.footballmanager_cvl.databinding.FragmentHistoricBinding;
import com.example.footballmanager_cvl.databinding.FragmentHomeBinding;
import com.example.footballmanager_cvl.databinding.FragmentSaveBinding;
import com.example.footballmanager_cvl.ui.home.HomeFragment;
import com.example.footballmanager_cvl.ui.home.HomeViewModel;
import com.example.footballmanager_cvl.ui.home.Jugador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SaveFragment extends Fragment {

    private SaveViewModel mViewModel;

    public static String fechaSeleccionada = null;

    TextView textFecha;
    ImageView fechaOk, fechaNoOk, convOk, convNoOk;

    private FragmentSaveBinding binding;

    ArrayList<Jugador> convocados = HomeFragment.convocados;

    private DatabaseReference mDatabase;


    public static SaveFragment newInstance() {
        return new SaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(SaveViewModel.class);

        binding = FragmentSaveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        textFecha = binding.textFecha;
        fechaOk = binding.imageFechOk;
        fechaNoOk = binding.imageFechNoOk;
        convOk = binding.imageJugOk;
        convNoOk = binding.imageJugNoOk;

        System.out.println("Fragmento save");
        /*for(Jugador c : convocados){
            System.out.println(c.getNombre() + fechaSeleccionada);
        }*/

        if(fechaSeleccionada==null && convocados.isEmpty()){
            Toast.makeText(getContext(), "Debes seleccionar una fecha y a los jugadores", Toast.LENGTH_SHORT).show();

        }else if(fechaSeleccionada!=null && convocados.isEmpty()){
            Toast.makeText(getContext(), "Debes seleccionar los jugadores", Toast.LENGTH_SHORT).show();
            fechaNoOk.setVisibility(View.GONE);
            fechaOk.setVisibility(View.VISIBLE);
        }else if(fechaSeleccionada==null && !convocados.isEmpty()){
            Toast.makeText(getContext(), "Debes seleccionar una fecha", Toast.LENGTH_SHORT).show();
            convNoOk.setVisibility(View.GONE);
            convOk.setVisibility(View.VISIBLE);
        }
        else{
            writeNewUser(convocados, fechaSeleccionada);
            textFecha.setText("ALINEACIÓN GUARDADA CON ÉXITO");
            textFecha.setTextColor(Color.GREEN);
            fechaNoOk.setVisibility(View.GONE);
            fechaOk.setVisibility(View.VISIBLE);
            convNoOk.setVisibility(View.GONE);
            convOk.setVisibility(View.VISIBLE);
            fechaSeleccionada = null;
            HomeFragment.reset();
        }


        return root;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SaveViewModel.class);
        // TODO: Use the ViewModel
    }

    //Metodo para guardar en la base de datos de firebase
    public void writeNewUser(ArrayList<Jugador>convocados, String fechaSeleccionada) {

        DatabaseReference refAlineaciones = mDatabase.child("Alineaciones").child(fechaSeleccionada);

        for (Jugador jugConv : convocados) {

            jugConv.setConvocado(true);
            // Genera una clave única para cada jugador
            String jugadorKey = refAlineaciones.push().getKey();
            // Establece los valores bajo esa clave
            refAlineaciones.child(jugadorKey).setValue(jugConv);
        }
    }


}