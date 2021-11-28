package com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Productos;

public class VerProductoTiendaFragment extends Fragment {

    private VerProductoTiendaViewModel mViewModel;
    View root;
    TextView txtNombre;
    TextView txtPrecio;
    RatingBar ratingBar;
    ImageView img;
    Button btnAgregar;
    Button btnAyuda;
    Button btnComprarAhora;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public static VerProductoTiendaFragment newInstance() {
        return new VerProductoTiendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ver_producto_tienda, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        Bundle arg=getArguments();
        String id = arg.getString("idProducto");
        txtNombre=root.findViewById(R.id.txtVerProdNombre);
        txtNombre.setText(id);


        Query queryMascota = databaseReference.child("Productos").orderByChild("idProducto").equalTo(id);
        queryMascota.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        Productos productos = objSnapshot.getValue(Productos.class);
                        txtNombre.setText(productos.getNombre());

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
        mViewModel = new ViewModelProvider(this).get(VerProductoTiendaViewModel.class);
        // TODO: Use the ViewModel
    }

}