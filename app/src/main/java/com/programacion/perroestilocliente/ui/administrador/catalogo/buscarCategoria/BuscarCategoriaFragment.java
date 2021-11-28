package com.programacion.perroestilocliente.ui.administrador.catalogo.buscarCategoria;

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

import java.util.ArrayList;

public class BuscarCategoriaFragment extends Fragment {

    private AutoCompleteTextView id;
    private TextView tvNombre,tvDesc,tvPublico;
    private ImageButton search;
    View root;
    int bnd=0;
    private BuscarCategoriaViewModel mViewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<ElementListView> arrayList;
    private SpnAdapterCategoria customAdapter;
    String cat="";

    public static BuscarCategoriaFragment newInstance() {
        return new BuscarCategoriaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_buscar_categoria, container, false);
        id = root.findViewById(R.id.spnBCatID);
        tvNombre = root.findViewById(R.id.tvBCatNom);;
        tvDesc = root.findViewById(R.id.tvBCatDesc);;
        tvPublico = root.findViewById(R.id.tvBCatPublic);;
        search = root.findViewById(R.id.btnBCatbusca);

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
                    Categorias c=null;
                    for (DataSnapshot objSnapshot: snapshot.getChildren()){
                        c = objSnapshot.getValue(Categorias.class);
                    }
                    if (c!=null){
                        tvNombre.setText(c.getNombreCategoria());
                        tvDesc.setText(c.getDescripcion());
                        tvPublico.setText(c.getTipoPublico());
                        CustomToast.mostarToast("Dato encontrado!",1,true,root,getLayoutInflater(),getContext());
                        bnd=1;
                    }else{
                        CustomToast.mostarToast("Dato NO encontrado!",3,false,root,getLayoutInflater(),getContext());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error){

                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BuscarCategoriaViewModel.class);
        // TODO: Use the ViewModel
    }

}