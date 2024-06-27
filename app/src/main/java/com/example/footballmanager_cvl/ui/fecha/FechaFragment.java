package com.example.footballmanager_cvl.ui.fecha;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.footballmanager_cvl.R;
import com.example.footballmanager_cvl.databinding.FragmentFechaBinding;
import com.example.footballmanager_cvl.databinding.FragmentGalleryBinding;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FechaFragment extends Fragment{

    private FechaViewModel mViewModel;
    private FragmentFechaBinding binding;

    public static FechaFragment newInstance() {
        return new FechaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFechaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DialogoFecha df = new DialogoFecha();
        df.show(getChildFragmentManager(), "Mi dialogo fecha");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FechaViewModel.class);
        // TODO: Use the ViewModel
    }


}