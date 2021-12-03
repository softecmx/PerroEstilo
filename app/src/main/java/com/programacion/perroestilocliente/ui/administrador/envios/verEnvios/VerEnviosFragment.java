package com.programacion.perroestilocliente.ui.administrador.envios.verEnvios;

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
import com.programacion.perroestilocliente.ui.administrador.envios.detalleEnvios.DetalleEnvioFragment;
import com.programacion.perroestilocliente.ui.administrador.inicio.ElementListViewInicioAdmin;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ListAdapterConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.verDetallePedido.VerPedidoFragment;

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

        root = inflater.inflate(R.layout.fragment_ver_envios, container, false);
        imgbtnBuscar = root.findViewById(R.id.ibtnBuscarPedidoEnvios);
        listView = root.findViewById(R.id.listVerEnvios);
        ArrayList<Item> arrayListItems = new ArrayList<>();

        iniciaFirebase();
        registerForContextMenu(listView);
        listarDatos();
        verdetalles();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerEnviosViewModel.class);
        // TODO: Use the ViewModel
    }


    public void listarDatos() {
        arrayListOrdenes.clear();
        databaseReference.child("OrdenesCliente/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewVerEnvios> arrayList = new ArrayList<>();

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

                                            if (estatus.equals("Preparando pedido")) {
                                                String id = snapshot.child("inOrden").getValue().toString();
                                                String total = snapshot.child("total").getValue().toString();
                                                String idusuario = snapshot.child("idCliente").getValue().toString();

                                                arrayListOrdenes.add(new ElementListViewVerEnvios(id, estatus, "$ " + total, idusuario));
                                                adapterOrdenes = new ListAdapterVerEnvios(getActivity(), arrayListOrdenes );
                                                listView.setAdapter(adapterOrdenes);
                                            }

                                        } else
                                            Log.i("NO hay objeto", snapshot + "");//si no se encuentran pedidos
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

                ElementListViewVerEnvios vistaElement= adapterOrdenes.getItem(position);

                DetalleEnvioFragment newFragment1 = new DetalleEnvioFragment();
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

}