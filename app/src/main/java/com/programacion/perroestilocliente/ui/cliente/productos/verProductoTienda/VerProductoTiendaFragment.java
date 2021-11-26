package com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class VerProductoTiendaFragment extends Fragment {

    private VerProductoTiendaViewModel mViewModel;

    public static VerProductoTiendaFragment newInstance() {
        return new VerProductoTiendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ver_producto_tienda, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerProductoTiendaViewModel.class);
        // TODO: Use the ViewModel
    }

}