package com.programacion.perroestilocliente.ui.cliente.productos.todosLosProductos;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.inicio.ReciclerViewAdapterProductos;

import java.util.ArrayList;

public class TodosProductosFragment extends Fragment {

    private TodosProductosViewModel mViewModel;
    private RecyclerView recyclerView;
    private RViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isLinear = true;
    View root;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    ArrayList<Productos> arrayListProductos = new ArrayList<>();

    public static TodosProductosFragment newInstance() {
        return new TodosProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_todos_productos, container, false);
        iniciarFirebase();
        this.recyclerView = root.findViewById(R.id.recViewTodosProductos);
        this.layoutManager = new LinearLayoutManager(root.getContext());
        FragmentManager fragmentManager = getFragmentManager();

        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListProductos.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Productos productos = objSnapshot.getValue(Productos.class);
                    arrayListProductos.add(productos);
                    adapter = new RViewAdapter(root.getContext(), arrayListProductos, fragmentManager);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                            StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    isLinear = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        this.adapter = new RViewAdapter(root.getContext(), arrayListProductos, fragmentManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        isLinear = false;


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TodosProductosViewModel.class);
        // TODO: Use the ViewModel
    }

    private void iniciarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
    }


}