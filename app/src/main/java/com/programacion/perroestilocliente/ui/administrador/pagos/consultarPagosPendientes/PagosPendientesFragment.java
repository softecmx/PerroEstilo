package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.programacion.perroestilocliente.ui.administrador.inicio.ElementListViewInicioAdmin;
import com.programacion.perroestilocliente.ui.administrador.pagos.modificarPagosPendientes.ModificarPagosPendientesFragment;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.verDetallePedido.VerPedidoFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        root = inflater.inflate(R.layout.fragment_ver_pagos, container, false);
        listView = root.findViewById(R.id.listPagosPendientes);
        btnBuscar = root.findViewById(R.id.ibtnBuscarPagoPendiente);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        registerForContextMenu(listView);
        listarDatos();
        verdetalles();
        return root;

    }

    public void listarDatos() {
        databaseReference.child("Usuarios/Clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListP.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Clientes clientes = objSnapshot.getValue(Clientes.class);
                    databaseReference.child("OrdenesCliente/" + clientes.getIdUsuario()).orderByChild("estatusOrden").equalTo("Pago pendiente").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotr) {

                            for (DataSnapshot objSnapshot2 : snapshotr.getChildren()) {
                                OrdenesCliente ordenesCliente = objSnapshot2.getValue(OrdenesCliente.class);
                                ElementListViewPagosPendientes nvo = new ElementListViewPagosPendientes();
                                assert ordenesCliente != null;
                                nvo.setOrdenPedido(ordenesCliente.getInOrden());
                                nvo.setTotalPedido(ordenesCliente.getTotal() + "");
                                nvo.setIdusuario(ordenesCliente.getIdCliente());
                                nvo.setStatusPedido(ordenesCliente.getEstatusOrden());
                                arrayListP.add(nvo);
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
    }


    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void verdetalles() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               ElementListViewPagosPendientes vistaElement = customAdapter.getItem(position);

                ModificarPagosPendientesFragment newFragment1 = new ModificarPagosPendientesFragment();
                Bundle args = new Bundle();
                args.putString("idOrden", vistaElement.getOrdenPedido());
                args.putString("status", vistaElement.getStatusPedido());
                args.putString("total", vistaElement.getTotalPedido());
                args.putString("idusuario", vistaElement.getIdusuario());
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PagosPendientesViewModel.class);
        // TODO: Use the ViewModel
    }

}