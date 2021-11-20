package com.programacion.perroestilocliente.ui.administrador.envios.confirmarEnvio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class ConfirmarEnvioFragment extends Fragment {

    private ConfirmarEnvioViewModel mViewModel;

    public static ConfirmarEnvioFragment newInstance() {
        return new ConfirmarEnvioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmar_envio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConfirmarEnvioViewModel.class);
        // TODO: Use the ViewModel
    }

}