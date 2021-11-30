package com.programacion.perroestilocliente.ui.administrador.catalogo.consularCategorias;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.catalogo.SpnAdapterCategoria;
import com.programacion.perroestilocliente.ui.administrador.catalogo.ElementListView;

import java.util.ArrayList;

public class ConsultarCategoriasFragment extends Fragment {

    View root;
    private ListView listView;
    private SpnAdapterCategoria customAdapter;

    private ConsultarCategoriasViewModel mViewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ElementListView catSelected;

    public static ConsultarCategoriasFragment newInstance() {
        return new ConsultarCategoriasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_consultar_categorias, container, false);
        listView = root.findViewById(R.id.listConsultaCategorias);
        iniciaFirebase();
        listarDatos();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catSelected = (ElementListView) parent.getItemAtPosition(position);
                View dialogoView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_categorias,null);

                ((TextView)dialogoView.findViewById(R.id.tvConsCatNombre)).setText(catSelected.getNombre());
                ((TextView)dialogoView.findViewById(R.id.tvConsCatpublico)).setText(catSelected.getTipo());
                ((TextView)dialogoView.findViewById(R.id.tvConsCatDesc)).setText(catSelected.getDesc());

                AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
                dialogo.setTitle("Categoría");
                dialogo.setView(dialogoView);
                dialogo.setNegativeButton("Aceptar",null);
                dialogo.show();
            }
        });
        return root;
    }
    public void listarDatos(){
        databaseReference.child("Categorias").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListView> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Categorias p = objSnapshot.getValue(Categorias.class);
                    arrayList.add(new ElementListView(p.getIdCategorias(),p.getNombreCategoria(),p.getTipoPublico(), p.getEstadoLogico(), p.getDescripcion()));
                    customAdapter = new SpnAdapterCategoria(getActivity(), arrayList);
                    listView.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarCategoriasViewModel.class);
        // TODO: Use the ViewModel
    }

}