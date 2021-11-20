package com.programacion.perroestilocliente.ui.cliente.kits.kitPersonalizadoPaso2;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class KitPersonalizadoPaso2Fragment extends Fragment {

    private KitPersonalizadoPaso2ViewModel mViewModel;

    public static KitPersonalizadoPaso2Fragment newInstance() {
        return new KitPersonalizadoPaso2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kit_personalizado_paso2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(KitPersonalizadoPaso2ViewModel.class);
        // TODO: Use the ViewModel
    }

}