package com.programacion.perroestilocliente.ui.administrador.catalogo.crearCatergoria;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.CustomToast;

import java.util.UUID;

public class CrearCategoriasFragment extends Fragment {

    private CrearCategoriasViewModel mViewModel;

    private EditText etNombre,etDesc;
    private RadioButton rbHumano,rbMascota;
    private Button agregar,limpiar;
    View root;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static CrearCategoriasFragment newInstance() {
        return new CrearCategoriasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_agregar_categorias, container, false);
        etNombre = root.findViewById(R.id.etACatNom);
        etDesc = root.findViewById(R.id.etACatDesc);
        rbHumano = root.findViewById(R.id.radioACatHumano);
        rbMascota = root.findViewById(R.id.radioACatMascota);
        agregar = root.findViewById(R.id.btnACatAgregar);
        limpiar = root.findViewById(R.id.btnACatLimpiar);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });

        iniciaFirebase();
        return root;
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void validar(){
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
            if (masc){
                cat.setTipoPublico("Humanos");
            }else {
                cat.setTipoPublico("Mascotas");
            }
            cat.setIdCategorias(UUID.randomUUID().toString());
            cat.setEstadoLogico("1");
            databaseReference.child("Categorias").child(cat.getIdCategorias()).setValue(cat);
            CustomToast.mostarToast("Datos registrado!",1,false,root,getLayoutInflater(),getContext());
            limpiar();
        }
    }
    public void limpiar(){
        etNombre.setText("");
        etDesc.setText("");
        rbHumano.setChecked(false);
        rbMascota.setChecked(false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearCategoriasViewModel.class);
        // TODO: Use the ViewModel
    }

}