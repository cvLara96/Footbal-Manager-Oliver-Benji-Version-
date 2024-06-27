package com.example.footballmanager_cvl.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballmanager_cvl.databinding.FragmentGalleryBinding;
import com.example.footballmanager_cvl.ui.home.HomeFragment;
import com.example.footballmanager_cvl.ui.home.Jugador;
import com.example.footballmanager_cvl.ui.home.MyAdapter;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    //Convocados
    //Creamos el adaptador
    MyAdapterConvocados adapterConvocados;
    RecyclerView recyclerViewConvocados;
    ImageButton delete;
    ArrayList<Jugador> convocados = HomeFragment.convocados;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        System.out.println("Fragmento convocados");
        /*for(Jugador j : convocados){
            System.out.println(j.getNombre());
        }*/

        delete = binding.imageButtonDelete;
        delete.setOnClickListener(this::borrarConvocados);

        //REFERENCIAMOS EL ADAPTADOR (recibe como parametros la lista de elementos y el contexto (de donde viene))
        adapterConvocados = new MyAdapterConvocados(convocados, getContext());

        //Referenciamos el recycler
        recyclerViewConvocados = binding.recyclerViewConvocados;

        recyclerViewConvocados.setHasFixedSize(true);
        //.setHasFixedSieze(true) se utiliza en un RecyclerView en Android y tiene un impacto en
        //el rendimiento al establecer si el tamaño del RecyclerView es fijo
        recyclerViewConvocados.setLayoutManager(new LinearLayoutManager(getContext()));
        //.setLayoutManager(new LinearLayoutManager(this)); establece un tipo de listado lineal
        //de arriba a abajo

        //ASIGNAMOS EL ADAPTADOR
        recyclerViewConvocados.setAdapter(adapterConvocados);

        final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Metodo de pulsado de borrar convocados:
    public void borrarConvocados (View v){

        if(convocados.isEmpty()){
            Toast.makeText(getContext(), "No hay jugadores convocados", Toast.LENGTH_SHORT).show();
        }
        recyclerViewConvocados.setVisibility(View.INVISIBLE);
        HomeFragment.reset();

    }

}