package com.programacion.perroestilocliente.ui.administrador.clientes;

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
import android.widget.ImageView;
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
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.clientes.ElementListViewClientes;
import com.programacion.perroestilocliente.ui.administrador.clientes.ListAdapterClientes;

import java.util.ArrayList;

public class VerClientesFragment extends Fragment implements AdapterView.OnItemClickListener {
    private TextView txtNombre, txtStatus;
    private ImageButton imgbtnLealtad, imgbtnBuscar;
    private ImageView fotoUsuario;

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
        registerForContextMenu(listView);
        //listarDatos();
        //modificarLealtad();
        return root;
    }



    public void listarDatos() {
        Toast.makeText(getContext(), "listarDatos", Toast.LENGTH_SHORT).show();

        /*databaseReference.child("Usuarios/Clientes").orderByChild("estadoLogico").equalTo("ACTIVO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewClientes> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    try {
                        Toast.makeText(getContext(), "cliente: "+objSnapshot, Toast.LENGTH_SHORT).show();
                        Clientes cl = objSnapshot.getValue(Clientes.class);
                        arrayList.add(new ElementListViewClientes(cl.getNombreCliente(),cl.getLealtad(),cl.getFotoPerfil()));
                        customAdapter = new ListAdapterClientes(getActivity(), arrayList);
                        listView.setAdapter(customAdapter);
                    }catch (Exception e)
                    {
                        Log.i("error: ",e.getMessage());
                    }

                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

    }
    private void modificarLealtad() {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}