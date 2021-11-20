package com.programacion.perroestilocliente.ui.administrador.disenios.crearDisenio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class CrearDiseniosFragment extends Fragment {

    private CrearDiseniosViewModel mViewModel;

    public static CrearDiseniosFragment newInstance() {
        return new CrearDiseniosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agregar_disenios, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearDiseniosViewModel.class);
        // TODO: Use the ViewModel
    }

}