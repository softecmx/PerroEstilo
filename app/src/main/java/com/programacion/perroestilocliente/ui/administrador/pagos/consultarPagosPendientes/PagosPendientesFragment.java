package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;

import java.util.ArrayList;

public class PagosPendientesFragment extends Fragment {
    private TextView txOrden, txtTotal, txtStatus;
    private PagosPendientesViewModel mViewModel;
    private ImageButton btnBuscar;

    View root;
    private ListView listView;
    private ListAdapterPagosPendientes customAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String inOrden = "";
    String total = "";
    String estatusOrden = "";

    public static PagosPendientesFragment newInstance() {
        return new PagosPendientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_ver_pagos, container, false);
        listView = root.findViewById(R.id.listPagosPendientes);
        btnBuscar = root.findViewById(R.id.ibtnBuscarPagoPendiente);

        iniciaFirebase();
        listarDatos();
        registerForContextMenu(listView);
        return root;

    }
    public void listarDatos() {

        databaseReference.child("OrdenesCliente").orderByChild("idCliente").orderByChild("inOrden").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewPagosPendientes> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) { //Subtotales de cada orden
                        OrdenesCliente oc = snapshot.getValue(OrdenesCliente.class);
                        inOrden =oc.getInOrden();
                        estatusOrden  =oc.getEstatusOrden();
                        //total =oc.getTotal();
                    arrayList.add(new ElementListViewPagosPendientes(inOrden ,Integer.parseInt(estatusOrden),Integer.parseInt(total)));
                    customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayList);
                    listView.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PagosPendientesViewModel.class);
        // TODO: Use the ViewModel
    }

}