package com.programacion.perroestilocliente.ui.cliente.productos.filtrarProductos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class FiltrarProductosFragment extends Fragment {

    private FiltrarProductosViewModel mViewModel;

    public static FiltrarProductosFragment newInstance() {
        return new FiltrarProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filtrar_productos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FiltrarProductosViewModel.class);
        // TODO: Use the ViewModel
    }

}