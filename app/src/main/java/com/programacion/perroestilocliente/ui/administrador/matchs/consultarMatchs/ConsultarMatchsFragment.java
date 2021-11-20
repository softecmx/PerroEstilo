package com.programacion.perroestilocliente.ui.administrador.matchs.consultarMatchs;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class ConsultarMatchsFragment extends Fragment {

    private ConsultarMatchsViewModel mViewModel;

    public static ConsultarMatchsFragment newInstance() {
        return new ConsultarMatchsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultar_matchs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarMatchsViewModel.class);
        // TODO: Use the ViewModel
    }

}