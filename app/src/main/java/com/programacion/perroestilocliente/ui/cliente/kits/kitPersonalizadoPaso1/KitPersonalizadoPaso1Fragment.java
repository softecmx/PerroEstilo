package com.programacion.perroestilocliente.ui.cliente.kits.kitPersonalizadoPaso1;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class KitPersonalizadoPaso1Fragment extends Fragment {

    private KitPersonalizadoPaso1ViewModel mViewModel;

    public static KitPersonalizadoPaso1Fragment newInstance() {
        return new KitPersonalizadoPaso1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kit_personalizado_paso1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(KitPersonalizadoPaso1ViewModel.class);
        // TODO: Use the ViewModel
    }

}