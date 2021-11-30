package com.programacion.perroestilocliente.ui.cliente.direcciones.verDireccion;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class VerDireccionFragment extends Fragment {


    private VerDireccionViewModel mViewModel;
    View root;

    public static VerDireccionFragment newInstance() {
        return new VerDireccionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ver_direccion, container, false);
        Bundle bound = getArguments();
        String id = bound.getString("idDireccion");
        System.out.println(id + " VER direccion");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerDireccionViewModel.class);
        // TODO: Use the ViewModel
    }

}