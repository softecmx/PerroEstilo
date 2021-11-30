package com.programacion.perroestilocliente.ui.administrador.clientes;

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
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.inventario.ElementListViewInventario;
import com.programacion.perroestilocliente.ui.administrador.inventario.ListAdapterInventario;

import java.util.ArrayList;

public class VerClientesFragment extends Fragment {
    private TextView txtNombre, txtStatus;
    private ImageButton imgbtnLealtad, imgbtnBuscar;

    View root;
    private ListView listView;
    private ListAdapterClientes customAdapter;
    private androidx.appcompat.app.AlertDialog dialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<Clientes> ListaClientes = new ArrayList<Clientes>();
    String nombre = "";
    String lealtad = "";

    private VerClientesViewModel mViewModel;

    public static VerClientesFragment newInstance() {
        return new VerClientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_ver_clientes, container, false);
        listView = root.findViewById(R.id.listClientes);
        txtNombre= root.findViewById(R.id.txtNombreUsuarioLealtadLista);
        txtStatus= root.findViewById(R.id.txtStatusUsuarioLealtadLista);
        imgbtnLealtad=root.findViewById(R.id.imgbtnLealtad);
        imgbtnBuscar = root.findViewById(R.id.ibtnAgregarClienteLeal);



        iniciaFirebase();
        listarDatos();
        registerForContextMenu(listView);
        return root;
    }
    public void listarDatos() {
/*
        databaseReference.child("Usuarios").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewClientes> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Clientes c = objSnapshot.getValue(Clientes.class);
                    //Lealtad si/no
                    databaseReference.child("Clientes").orderByChild("idModelo").equalTo(c.getDisenio()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                Disenios d = objSnapshot.getValue(Disenios.class);
                                disenio = d.getDisenio();

                                            arrayList.add(new ElementListViewInventario(p.getIdProducto(), p.getNombre(), disenio, tallas, p.getEstatusStock(), p.getStock()));
                                            customAdapter = new ListAdapterInventario(getActivity(), arrayList);
                                            listView.setAdapter(customAdapter);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerClientesViewModel.class);
        // TODO: Use the ViewModel
    }

}