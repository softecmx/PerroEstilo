package com.programacion.perroestilocliente.ui.administrador.pagos.modificarPagosPendientes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacion.perroestilocliente.R;

public class ModificarPagosPendientesFragment extends Fragment {
    Button btnConfirmarPago;
    Button btnAtras;
    TextView status,fecha,orden,serie,fechaDetalle,nombreContacto,telContacto,direccionContacto;
    TextView total;
    ListView listView;
    View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ModificarPagosPendientesViewModel mViewModel;

    public static ModificarPagosPendientesFragment newInstance() { return new ModificarPagosPendientesFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_detalle_pagos, container, false);
        btnConfirmarPago=root.findViewById(R.id.btnConfirmarDetallePago);
        btnAtras=root.findViewById(R.id.btnAtrasDetaPago);
        return root;
    }
    public void inicializarComponentes(){
        status= root.findViewById(R.id.txtStatusDetPagos);
        fecha=root.findViewById(R.id.txtFechaDetPagos);
        orden= root.findViewById(R.id.txtNoOrdenDetPagos);
        serie= root.findViewById(R.id.txtNoSerieDetPagos);
        fechaDetalle=root.findViewById(R.id.txtFechaEstimadaDetPago);
        nombreContacto=root.findViewById(R.id.txtContactoDetPago);
        telContacto=root.findViewById(R.id.txtTelefonoDetPago);
        direccionContacto= root.findViewById(R.id.txtDireccionDetPago);
        total=root.findViewById(R.id.txtTotalDetPago);
    }
    public void botones(){
        if(btnConfirmarPago.callOnClick()){
            btnConfirmarPago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               //llamar al fragment_ver/consultar_pedidos
                }
            });
        }
        if(btnAtras.callOnClick()){
            btnAtras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //regresar al fragment consultar pagos
                      }
            });
        }
    }
    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ModificarPagosPendientesViewModel.class);
        // TODO: Use the ViewModel
    }

}