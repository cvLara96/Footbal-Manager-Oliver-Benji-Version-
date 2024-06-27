package com.example.footballmanager_cvl.ui.signout;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footballmanager_cvl.MainActivity;
import com.example.footballmanager_cvl.R;

public class SignoutFragment extends Fragment {

    private SignoutViewModel mViewModel;

    public static SignoutFragment newInstance() {
        return new SignoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signout, container, false);


        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignoutViewModel.class);

        // TODO: Use the ViewModel
    }

}