package com.programacion.perroestilocliente.ui.cliente.productos.verProducto;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class VerProductoFragment extends Fragment {

    private VerProductoViewModel mViewModel;

    public static VerProductoFragment newInstance() {
        return new VerProductoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,

                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_ver_producto, container, false);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerProductoViewModel.class);
        // TODO: Use the ViewModel
    }

}