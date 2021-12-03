package com.programacion.perroestilocliente.ui.administrador.clientes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageButton imgbtnBuscar;
    private ImageView fotoUsuario;


    private Button imgbtnLealtad;
    View root;
    private ListView listView;
    private ListAdapterClientes customAdapter;
    private androidx.appcompat.app.AlertDialog dialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<Clientes> ListaClientes = new ArrayList<Clientes>();
    String nombre = "";
    String lealtad = "";
    String img ="";

    private VerClientesViewModel mViewModel;

    public static VerClientesFragment newInstance() {
        return new VerClientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_ver_clientes, container, false);
        //root= inflater.inflate(R.layout.item_lista_clientes_lealtad, container, false);
        listView = root.findViewById(R.id.listClientes);
        imgbtnBuscar = root.findViewById(R.id.ibtnAgregarClienteLeal);
        txtNombre= root.findViewById(R.id.txtNombreUsuarioLealtadLista);
        txtStatus= root.findViewById(R.id.txtStatusUsuarioLealtad);
        imgbtnLealtad=root.findViewById(R.id.imgbtnLealtad);

        iniciaFirebase();
        listarDatos();
        registerForContextMenu(listView);
        return root;
    }
    public void listarDatos() {
/*
        databaseReference.child("Usuarios/Clientes").orderByChild("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewConsultarPedidos> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Clientes cl = objSnapshot.getValue(Clientes.class);

                    arrayList.add(new ElementListViewConsultarPedidos(cl.getIdUsuario(),cl.getEstatus(),cl.getNombreCliente(),cl.getApellidoPaterno(),cl.getEmail(),cl.getTelefono()));
                    customAdapter = new ListAdapterConsultarPedidos(getActivity(), arrayList);
                    listView.setAdapter(customAdapter);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

*/
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