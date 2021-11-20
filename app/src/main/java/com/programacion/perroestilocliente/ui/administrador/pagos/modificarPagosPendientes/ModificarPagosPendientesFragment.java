package com.programacion.perroestilocliente.ui.administrador.pagos.modificarPagosPendientes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class ModificarPagosPendientesFragment extends Fragment {

    private ModificarPagosPendientesViewModel mViewModel;

    public static ModificarPagosPendientesFragment newInstance() {
        return new ModificarPagosPendientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modificar_pagos_pendientes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ModificarPagosPendientesViewModel.class);
        // TODO: Use the ViewModel
    }

}