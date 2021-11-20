package com.programacion.perroestilocliente.ui.cliente.productos.crearProductoParaPedido;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class CrearProductoPedidoFragment extends Fragment {

    private CrearProductoPedidoViewModel mViewModel;

    public static CrearProductoPedidoFragment newInstance() {
        return new CrearProductoPedidoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agregar_producto_pedido, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearProductoPedidoViewModel.class);
        // TODO: Use the ViewModel
    }

}