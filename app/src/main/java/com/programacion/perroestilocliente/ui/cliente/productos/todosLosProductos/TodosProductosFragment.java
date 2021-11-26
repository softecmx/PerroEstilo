package com.programacion.perroestilocliente.ui.cliente.productos.todosLosProductos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class TodosProductosFragment extends Fragment {

    private TodosProductosViewModel mViewModel;
    private RecyclerView recyclerView;
    private RViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isLinear = true;
    View root;

    public static TodosProductosFragment newInstance() {
        return new TodosProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_todos_productos, container, false);
        init();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // if (isLinear) {

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        isLinear = false;
       /* } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(adapter);
            isLinear = true;
        }*/


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TodosProductosViewModel.class);
        // TODO: Use the ViewModel
    }

    private void init() {
        this.recyclerView = root.findViewById(R.id.recViewTodosProductos);
        this.layoutManager = new LinearLayoutManager(root.getContext());
        this.adapter = new RViewAdapter(root.getContext(), getListaCoche());
    }


    private ArrayList<Producto> getListaCoche() {
        ArrayList<Producto> arrayListProductos = new ArrayList<>();

        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        arrayListProductos.add(new Producto(R.drawable.monio_corbatin_anaranjado, "McLaren P1", true, true, "200", "0", "4.5"));
        return arrayListProductos;
    }

}