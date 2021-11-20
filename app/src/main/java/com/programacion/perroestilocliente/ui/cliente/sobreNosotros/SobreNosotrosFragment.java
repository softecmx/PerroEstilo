package com.programacion.perroestilocliente.ui.cliente.sobreNosotros;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

public class SobreNosotrosFragment extends Fragment {

    private SobreNosotrosViewModel mViewModel;

    public static SobreNosotrosFragment newInstance() {
        return new SobreNosotrosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sobre_nosotros, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SobreNosotrosViewModel.class);
        // TODO: Use the ViewModel
    }

}