package com.programacion.perroestilocliente.ui.administrador.matchs.crearMatch;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.programacion.perroestilocliente.R;

public class CrearMath1Fragment extends Fragment {

    private CrearMath1ViewModel mViewModel;
    Button btnSiguiente;
    View root;
    public static CrearMath1Fragment newInstance() {
        return new CrearMath1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_agregar_math1, container, false);
        btnSiguiente = root.findViewById(R.id.btnckConfitma);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.programacion.perroestilocliente.ui.administrador.matchs.crearMatch.CrearMatch2Fragment newFragment1= new com.programacion.perroestilocliente.ui.administrador.matchs.crearMatch.CrearMatch2Fragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearMath1ViewModel.class);
        // TODO: Use the ViewModel
    }

}