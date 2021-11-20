package com.programacion.perroestilocliente.ui.cliente.comprar.instruccionesCompra;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class InstruccionesCompraFragment extends Fragment {

    private InstruccionesCompraViewModel mViewModel;

    public static InstruccionesCompraFragment newInstance() {
        return new InstruccionesCompraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instrucciones_compra, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InstruccionesCompraViewModel.class);
        // TODO: Use the ViewModel
    }

}