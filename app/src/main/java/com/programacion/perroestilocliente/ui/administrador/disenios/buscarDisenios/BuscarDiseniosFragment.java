package com.programacion.perroestilocliente.ui.administrador.disenios.buscarDisenios;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.programacion.perroestilocliente.R;

public class BuscarDiseniosFragment extends Fragment {

    public static BuscarDiseniosFragment newInstance() {
        return new BuscarDiseniosFragment();
    }

    private BuscarDiseniosViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscar_disenios, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BuscarDiseniosViewModel.class);
        // TODO: Use the ViewModel
    }

}