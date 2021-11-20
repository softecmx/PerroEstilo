package com.programacion.perroestilocliente.ui.cliente.pedidos.estadoPedido;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class EstadoPedidoFragment extends Fragment {

    private EstadoPedidoViewModel mViewModel;

    public static EstadoPedidoFragment newInstance() {
        return new EstadoPedidoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estado_pedido, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EstadoPedidoViewModel.class);
        // TODO: Use the ViewModel
    }

}