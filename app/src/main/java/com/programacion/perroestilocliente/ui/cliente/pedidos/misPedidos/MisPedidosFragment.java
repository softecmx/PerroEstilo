package com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;

import java.util.ArrayList;

public class MisPedidosFragment extends Fragment {

    private MisPedidosViewModel mViewModel;
    View root;
    androidx.recyclerview.widget.RecyclerView reciclerViewOrdenes;
    ArrayList<OrdenesCliente> arrayListOrdenes = new ArrayList<>();
    private ReciclerViewAdapterPedidos adapterOrdenes;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public static MisPedidosFragment newInstance() {
        return new MisPedidosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reciclerViewOrdenes=root.findViewById(R.id.listMisPedidos);
        final String[] idCliente = {""};
        Query queryCliente = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(user.getEmail());
        queryCliente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        Clientes usuario = objSnapshot.getValue(Clientes.class);
                        idCliente[0] =usuario.getIdUsuario();
                        databaseReference.child("OrdenesCliente/"+ idCliente[0]).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                arrayListOrdenes.clear();
                                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                                    OrdenesCliente orden = objSnapshot.getValue(OrdenesCliente.class);
                                    arrayListOrdenes.add(orden);
                                    adapterOrdenes = new ReciclerViewAdapterPedidos(root.getContext(), arrayListOrdenes, getFragmentManager());
                                    reciclerViewOrdenes.setLayoutManager(new LinearLayoutManager(getContext()));
                                    reciclerViewOrdenes.setAdapter(adapterOrdenes);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MisPedidosViewModel.class);
        // TODO: Use the ViewModel
    }

}