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
import com.google.firebase.storage.FirebaseStorage;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Clientes;
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
    ArrayList<ElementListViewPagosPendientes> arrayListP = new ArrayList<>();
    public static PagosPendientesFragment newInstance() {
        return new PagosPendientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_ver_pagos, container, false);
        listView = root.findViewById(R.id.listPagosPendientes);
        btnBuscar = root.findViewById(R.id.ibtnBuscarPagoPendiente);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Usuarios/Clientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListP.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Clientes clientes = objSnapshot.getValue(Clientes.class);

                    databaseReference.child("OrdenesCliente/"+clientes.getIdUsuario()).orderByChild("estatusOrden").equalTo("Pago pendiente").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                OrdenesCliente ordenesCliente = objSnapshot.getValue(OrdenesCliente.class);
                                assert ordenesCliente != null;
                                System.out.println("ss"+ordenesCliente.getEstatusOrden());
                                arrayListP.add(new ElementListViewPagosPendientes(ordenesCliente.getEstatusOrden(),ordenesCliente.getInOrden() ,Float.parseFloat(String.valueOf(ordenesCliente.getTotal()))));
                                customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayListP);
                                listView.setAdapter(customAdapter);
                                //lstProdModa.add(productos);
                                //listAdapterProductosModa = new ListAdapterProductosModa(getActivity(), lstProdModa);
                                // lstProductosModa.setAdapter(listAdapterProductosModa);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /* databaseReference.child("OrdenesCliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              arrayListP.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) { //Subtotales de cada orden
                    OrdenesCliente oc = objSnapshot.getValue(OrdenesCliente.class);
                    //  inOrden =oc.getInOrden();
                    //  estatusOrden  =oc.getEstatusOrden();
                    //  total =oc.getTotal();
                    System.out.println(oc.getEstatusOrden()+" ");
                    arrayListP.add(new ElementListViewPagosPendientes(oc.getEstatusOrden(),oc.getInOrden() ,Float.parseFloat(String.valueOf(oc.getTotal()))));
                    customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayListP);
                    listView.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

         */
        registerForContextMenu(listView);
        return root;

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