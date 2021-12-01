package com.programacion.perroestilocliente.ui.administrador.ventas.consultarVentas;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacion.perroestilocliente.R;

public class ConsultarVentasFragment extends Fragment {
    private TextView txtFecha, noVentas, canTotalVentas;
    private ConsultarVentasViewModel mViewModel;
    private ImageButton btnBuscar;

    View root;
    private ListView listView;
    private ListAdapterVentas customAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String fecha = "";
    String ventas = "";
    String statusOrden = "";

    public static ConsultarVentasFragment newInstance() {
        return new ConsultarVentasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultar_ventas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarVentasViewModel.class);
        // TODO: Use the ViewModel
    }

}