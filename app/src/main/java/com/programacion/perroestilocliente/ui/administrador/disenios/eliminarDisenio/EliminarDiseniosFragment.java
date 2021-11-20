package com.programacion.perroestilocliente.ui.administrador.disenios.eliminarDisenio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class EliminarDiseniosFragment extends Fragment {

    private EliminarDiseniosViewModel mViewModel;

    public static EliminarDiseniosFragment newInstance() {
        return new EliminarDiseniosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eliminar_disenios, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EliminarDiseniosViewModel.class);
        // TODO: Use the ViewModel
    }

}