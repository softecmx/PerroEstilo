package com.programacion.perroestilocliente.ui.administrador.envios.verEnvios;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.ui.administrador.inicio.ElementListViewInicioAdmin;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ListAdapterConsultarPedidos;

import java.time.LocalDate;
import java.util.ArrayList;

public class VerEnviosFragment extends Fragment {
    View root;
    ImageView imgbtnBuscar;
    private VerEnviosViewModel mViewModel;
    ListView listView;
    ArrayList<ElementListViewVerEnvios> arrayListOrdenes = new ArrayList<>();
    private ListAdapterVerEnvios adapterOrdenes;
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static VerEnviosFragment newInstance() {
        return new VerEnviosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.fragment_ver_envios, container, false);
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imgbtnBuscar=root.findViewById(R.id.ibtnBuscarPedidoEnvios);
        listView = root.findViewById(R.id.listVerEnvios);
        ArrayList<Item> arrayListItems = new ArrayList<>();

        iniciaFirebase();
        listarDatos();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerEnviosViewModel.class);
        // TODO: Use the ViewModel
    }
    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void listarDatos() {
        databaseReference.child("OrdenesCliente/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewInicioAdmin> arrayList = new ArrayList<>();

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Log.i("ids clientes ", objSnapshot.getKey());

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

                                            if (estatus.equals("En camino")) {
                                                String id = snapshot.child("inOrden").getValue().toString();
                                                String total = snapshot.child("total").getValue().toString();
                                                LocalDate todaysDate = LocalDate.now();

                                                arrayListOrdenes.add(new ElementListViewVerEnvios(id, estatus, "$ " + total,todaysDate+""));
                                                adapterOrdenes = new ListAdapterVerEnvios(getActivity(), arrayListOrdenes);
                                                listView.setAdapter(adapterOrdenes);
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
}