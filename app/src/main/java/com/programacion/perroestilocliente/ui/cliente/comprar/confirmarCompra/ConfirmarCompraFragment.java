package com.programacion.perroestilocliente.ui.cliente.comprar.confirmarCompra;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class ConfirmarCompraFragment extends Fragment {

    private ConfirmarCompraViewModel mViewModel;

    public static ConfirmarCompraFragment newInstance() {
        return new ConfirmarCompraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmar_compra, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConfirmarCompraViewModel.class);
        // TODO: Use the ViewModel
    }

}