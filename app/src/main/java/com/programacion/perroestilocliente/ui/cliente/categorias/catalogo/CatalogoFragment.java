package com.programacion.perroestilocliente.ui.cliente.categorias.catalogo;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.ui.administrador.catalogo.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.catalogo.SpnAdapterCategoria;
import com.programacion.perroestilocliente.ui.cliente.inicio.ListAdapterCategorias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogoFragment extends Fragment {

    private CatalogoViewModel mViewModel;
    View root;
    ArrayList<ElementListView> arrayList;

    private SpnAdapterCategoria customAdapter;
    ElementListView catSelected;
    ListView lstViewCategorias;
    AutoCompleteTextView spinCatalogoCategorias;
    List<Categorias> lstCategorias = new ArrayList<>();
    HashMap<String, Categorias> hashCategorias = new HashMap<>();
    ListAdapterCategorias listAdapterCateforias;

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
       // spinCatalogoCategorias=root.findViewById(R.id.spinCatalogoCategorias);

        iniciarFirebase();
        listarDatos();
        lstViewCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catSelected = (ElementListView) parent.getItemAtPosition(position);
              //Remplazar por un fragment

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

    public void listarDatos(){
        databaseReference.child("Categorias").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Categorias p = objSnapshot.getValue(Categorias.class);
                    arrayList.add(new ElementListView(p.getIdCategorias(),p.getNombreCategoria(),p.getTipoPublico(), p.getEstadoLogico(), p.getDescripcion()));
                    customAdapter = new SpnAdapterCategoria(getActivity(), arrayList);
                    lstViewCategorias.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}