package com.programacion.perroestilocliente.ui.administrador.catalogo.eliminarCategoria;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.ImageButton;
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
import com.programacion.perroestilocliente.ui.administrador.catalogo.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.catalogo.SpnAdapterCategoria;
import com.programacion.perroestilocliente.ui.administrador.catalogo.buscarCategoria.BuscarCategoriaViewModel;

import java.util.ArrayList;

public class EliminarCategoriaFragment extends Fragment {

    private AutoCompleteTextView id;
    private TextView tvNombre,tvDesc,tvPublico;
    private ImageButton search;
    private Button eliminar,limpia;
    View root;
    int bnd=0;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<ElementListView> arrayList;
    private SpnAdapterCategoria customAdapter;
    String cat="";

    private EliminarCategoriaViewModel mViewModel;

    public static EliminarCategoriaFragment newInstance() {
        return new EliminarCategoriaFragment();
    }
    Categorias c;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_eliminar_categoria, container, false);
        id = root.findViewById(R.id.spnElimCatID);
        tvNombre = root.findViewById(R.id.tvElimCatNom);;
        tvDesc = root.findViewById(R.id.tvElimCatDesc);;
        tvPublico = root.findViewById(R.id.tvElimCatPublic);;
        search = root.findViewById(R.id.btnElimCatbusca);
        eliminar = root.findViewById(R.id.btnElimCat);
        limpia = root.findViewById(R.id.btnElimCatLimpia);
        iniciaFirebase();
        llenarSpn();
        id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cat=customAdapter.getItem(position).getId();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valida();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elimina();
            }
        });
        limpia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpia();
            }
        });
        return root;
    }
    public void llenarSpn(){
        databaseReference.child("Categorias").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList = new ArrayList<>();
                id.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Categorias p = objSnapshot.getValue(Categorias.class);
                    arrayList.add(new ElementListView(p.getIdCategorias(),p.getNombreCategoria(),p.getTipoPublico(), p.getEstadoLogico(), p.getDescripcion()));
                    customAdapter = new SpnAdapterCategoria(getActivity(), arrayList);
                    id.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void valida(){
        AutoCompleteTextView idACTV = id;
        String id = cat;
        if (id.equals("")){
            CustomToast.mostarToast("Seleccione un dato",3,false,root,getLayoutInflater(),getContext());
        }else{
            databaseReference.child("Categorias").orderByChild("idCategorias").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot){
                    c=null;
                    for (DataSnapshot objSnapshot: snapshot.getChildren()){
                        c = objSnapshot.getValue(Categorias.class);
                    }
                    if (c!=null){
                        tvNombre.setText(c.getNombreCategoria());
                        tvDesc.setText(c.getDescripcion());
                        tvPublico.setText(c.getTipoPublico());
                        CustomToast.mostarToast("Dato encontrado!",1,true,root,getLayoutInflater(),getContext());
                        bnd=1;
                        search.setEnabled(false);
                        idACTV.setEnabled(false);
                        idACTV.setAdapter(null);
                    }else{
                        CustomToast.mostarToast("Dato NO encontrado!",3,false,root,getLayoutInflater(),getContext());
                        bnd=0;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error){

                }
            });
        }
    }
    public void limpia(){
        cat="";
        tvDesc.setText("");
        tvNombre.setText("");
        tvPublico.setText("");
        id.setText("");
        search.setEnabled(true);
        llenarSpn();
        id.setEnabled(true);
    }
    public void elimina(){
        if (bnd!=0){
            c.setEstadoLogico("0");
            databaseReference.child("Categorias").child(c.getIdCategorias()).setValue(c);
            CustomToast.mostarToast("Elemento eliminado",1,false,root,getLayoutInflater(),getContext());
            bnd=0;
            limpia();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EliminarCategoriaViewModel.class);
        // TODO: Use the ViewModel
    }

}