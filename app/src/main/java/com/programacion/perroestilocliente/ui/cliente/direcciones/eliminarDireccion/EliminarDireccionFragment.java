package com.programacion.perroestilocliente.ui.cliente.direcciones.eliminarDireccion;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class EliminarDireccionFragment extends Fragment {

    private EliminarDireccionViewModel mViewModel;

    public static EliminarDireccionFragment newInstance() {
        return new EliminarDireccionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eliminar_direccion, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EliminarDireccionViewModel.class);
        // TODO: Use the ViewModel
    }

}