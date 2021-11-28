package com.programacion.perroestilocliente.ui.cliente.categorias.catalogo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.ui.cliente.inicio.ListAdapterCategorias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogoFragment extends Fragment {

    private CatalogoViewModel mViewModel;
    View root;

    ListView lstViewCategorias;
    AutoCompleteTextView spinCatalogoCategorias;
    List<Categorias> lstCategorias = new ArrayList<>();
    HashMap<String, Categorias> hashCategorias = new HashMap<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public static CatalogoFragment newInstance() {
        return new CatalogoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_catalogo, container, false);
        lstViewCategorias=root.findViewById(R.id.lstCatalogoCategorias);
        spinCatalogoCategorias=root.findViewById(R.id.spinCatalogoCategorias);

        iniciarFirebase();

        databaseReference.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstCategorias.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Categorias categoria = objSnapshot.getValue(Categorias.class);
                    lstCategorias.add(categoria);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);
        // TODO: Use the ViewModel
    }
    private void iniciarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
    }
}