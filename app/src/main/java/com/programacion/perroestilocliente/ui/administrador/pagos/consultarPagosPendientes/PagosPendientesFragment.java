package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.DetOrdenProductos;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.inventario.ElementListViewInventario;
import com.programacion.perroestilocliente.ui.administrador.inventario.ListAdapterInventario;

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

    String idOrden = "";
    String total = "";
    String statusOrden = "";

    public static PagosPendientesFragment newInstance() {
        return new PagosPendientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_pagos_pendientes, container, false);
        listView = root.findViewById(R.id.listPagosPendientes);
        btnBuscar = root.findViewById(R.id.ibtnBuscarPagoPendiente);

        iniciaFirebase();
        listarDatos();
        registerForContextMenu(listView);
        return root;

    }
    public void listarDatos() {

        databaseReference.child("DetalleOrdenesCliente").orderByChild("idOrdenCliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewPagosPendientes> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) { //Subtotales de cada orden

                    if(snapshot.exists()){
                        DetOrdenProductos dop = snapshot.getValue(DetOrdenProductos.class);
                        String idOrdenCliente =dop.getIdOrdenCliente();
                        String idDetOrdenProducto  =dop.getIdDetOrdenProducto();

                        String subtotal =dop.getSubtotal();
                        Log.i(subtotal,"subtotal: ");
                    }
                    else Toast.makeText(getContext(), "no se encontro datos", Toast.LENGTH_SHORT).show();

/*
                    databaseReference.child("OrdenesCliente").child("idOrdenCliente").orderByChild("idCliente").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                OrdenesCliente oc = objSnapshot.getValue(OrdenesCliente.class);
                                String estatusOrden=oc.getEstatusOrden();

                                idOrden=oc.getInOrden();
                                statusOrden=oc.getEstatusOrden();
                                total= dop.getSubtotal();
                                arrayList.add(new ElementListViewPagosPendientes( statusOrden,Integer.parseInt(total),Integer.parseInt(idOrden)));
                                customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayList);
                                listView.setAdapter(customAdapter);
                                arrayList.add(new ElementListViewPagosPendientes( estatusOrden,Integer.parseInt(subtotal),Integer.parseInt(idDetOrdenProducto)));
                                customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayList);
                                listView.setAdapter(customAdapter);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });*/
                }
/*

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    OrdenesCliente oc = objSnapshot.getValue(OrdenesCliente.class);
                    databaseReference.child("OrdenesCliente").orderByChild("inOrden").equalTo(oc.getInOrden()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                DetOrdenProductos dop = objSnapshot.getValue(DetOrdenProductos.class);
                                idOrden=oc.getInOrden();
                                statusOrden=oc.getEstatusOrden();
                                total= dop.getSubtotal();
                                arrayList.add(new ElementListViewPagosPendientes( statusOrden,Integer.parseInt(total),Integer.parseInt(idOrden)));
                                customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayList);
                                listView.setAdapter(customAdapter);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }*/
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