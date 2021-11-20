package com.programacion.perroestilocliente.ui.administrador.ventas.verDetalleVenta;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class VerDetalleVentaFragment extends Fragment {

    private VerDetalleVentaViewModel mViewModel;

    public static VerDetalleVentaFragment newInstance() {
        return new VerDetalleVentaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ver_detalle_venta, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerDetalleVentaViewModel.class);
        // TODO: Use the ViewModel
    }

}