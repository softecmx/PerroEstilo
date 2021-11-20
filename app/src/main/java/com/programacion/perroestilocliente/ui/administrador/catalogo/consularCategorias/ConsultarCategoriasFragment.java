package com.programacion.perroestilocliente.ui.administrador.catalogo.consularCategorias;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class ConsultarCategoriasFragment extends Fragment {

    private ConsultarCategoriasViewModel mViewModel;

    public static ConsultarCategoriasFragment newInstance() {
        return new ConsultarCategoriasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultar_categorias, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarCategoriasViewModel.class);
        // TODO: Use the ViewModel
    }

}