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

        registerForContextMenu(listView);
        listarDatos();
        verdetalles();
        return root;

    }

    public void listarDatos() {
        databaseReference.child("OrdenesCliente/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayListP.clear();

                ArrayList<ElementListViewPagosPendientes> arrayList = new ArrayList<>();

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Log.i("ids clientes ", objSnapshot.getKey());

/*
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
*/
                    databaseReference.child("OrdenesCliente/" + objSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot2 : snapshot.getChildren()) {
                                Log.i("Construyendoruta ", "OrdenesCliente/" + objSnapshot.getKey() + "/" + objSnapshot2.getKey());
                                databaseReference.child("OrdenesCliente/" + objSnapshot.getKey() + "/" + objSnapshot2.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {

                                            String estatus = snapshot.child("estatusOrden").getValue().toString();

                                            if (estatus.equals("Pago pendiente")) {
                                                String id = snapshot.child("inOrden").getValue().toString();
                                                String total = snapshot.child("total").getValue().toString();
                                                String idusuario = snapshot.child("idCliente").getValue().toString();

                                                arrayListP.add(new ElementListViewPagosPendientes(id, estatus, "$ " + total,idusuario));
                                                customAdapter = new ListAdapterPagosPendientes(getActivity(), arrayListP);
                                                listView.setAdapter(customAdapter);
                                            }

                                        } else
                                            Log.i("NO hay objeto", snapshot + "");//si no se encuentran pedidos
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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

                ElementListViewPagosPendientes vistaElement= customAdapter.getItem(position);

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