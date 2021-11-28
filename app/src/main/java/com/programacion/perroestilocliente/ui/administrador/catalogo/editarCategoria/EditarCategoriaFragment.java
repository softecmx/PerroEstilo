package com.programacion.perroestilocliente.ui.administrador.catalogo.editarCategoria;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

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
import java.util.UUID;

public class EditarCategoriaFragment extends Fragment{

    private EditarCategoriaViewModel mViewModel;
    private AutoCompleteTextView id;
    private EditText etNombre,etDesc;
    private RadioButton rbHumano,rbMascota;
    private Button modificar,limpiar;
    private ImageButton search;
    View root;
    int bnd=0;
    private SpnAdapterCategoria customAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<ElementListView> arrayList;

    String cat="";

    public static EditarCategoriaFragment newInstance() {
        return new EditarCategoriaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root =inflater.inflate(R.layout.fragment_editar_categoria, container, false);
        id = root.findViewById(R.id.spnEditarCatID);
        etNombre = root.findViewById(R.id.etEditaCatNom);
        etDesc = root.findViewById(R.id.etEditaCatDesc);
        rbHumano = root.findViewById(R.id.radioEditaCatHumano);
        rbMascota = root.findViewById(R.id.radioEditaCatMascota);
        modificar = root.findViewById(R.id.btnEditaCatModifica);
        limpiar = root.findViewById(R.id.btnEditaCatLimpiar);
        search = root.findViewById(R.id.btnEditarCatbusca);

        iniciaFirebase();
        llenarSpn();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busca();
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valida();
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
        id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cat = customAdapter.getItem(position).getId();
            }
        });
        return root;
    }
    public void limpiar(){
        cat="";
        bnd=0;
        id.setText("");
        etNombre.setText("");
        etDesc.setText("");
        rbMascota.setChecked(false);
        rbHumano.setChecked(false);
        llenarSpn();
        id.setEnabled(true);
        search.setEnabled(true);
    }
    public void valida(){
        if (bnd==0){
            CustomToast.mostarToast("Seleccione una categoría",2,false,root,getLayoutInflater(),getContext());
        }else{
            String nom = etNombre.getText().toString();
            String desc = etDesc.getText().toString();
            boolean masc = rbMascota.isChecked();
            boolean human = rbHumano.isChecked();
            if (nom.equals("") || (!masc && !human)){
                CustomToast.mostarToast("Error en los datos",2,false,root,getLayoutInflater(),getContext());
            }else{
                Categorias cat = new Categorias();
                cat.setNombreCategoria(nom);
                cat.setDescripcion(desc);
                if (human){
                    cat.setTipoPublico("Humanos");
                }else {
                    cat.setTipoPublico("Mascotas");
                }
                cat.setIdCategorias(this.cat);
                cat.setEstadoLogico("1");
                databaseReference.child("Categorias").child(cat.getIdCategorias()).setValue(cat);
                CustomToast.mostarToast("Dato modificado!",1,false,root,getLayoutInflater(),getContext());

                limpiar();
                llenarSpn();
            }
        }
    }

    public void busca(){
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
                        etNombre.setText(c.getNombreCategoria());
                        etDesc.setText(c.getDescripcion());
                        if (c.getTipoPublico().equals("Mascotas")){
                            rbHumano.setChecked(false);
                            rbMascota.setChecked(true);
                        }else if (c.getTipoPublico().equals("Humanos")){
                            rbHumano.setChecked(true);
                            rbMascota.setChecked(false);
                        }
                        CustomToast.mostarToast("Dato encontrado!",1,true,root,getLayoutInflater(),getContext());
                        bnd=1;
                        search.setEnabled(false);
                        idACTV.setAdapter(null);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarCategoriaViewModel.class);
        // TODO: Use the ViewModel
    }
}