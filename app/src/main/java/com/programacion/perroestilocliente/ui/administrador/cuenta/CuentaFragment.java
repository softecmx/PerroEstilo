package com.programacion.perroestilocliente.ui.administrador.cuenta;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Administrador;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.Usuarios;
import com.programacion.perroestilocliente.ui.cliente.perfil.ElementListView;

import java.util.ArrayList;


public class CuentaFragment extends Fragment {

    private TextView nombre,correo;
    private ImageButton siguiente;
    private ImageView perfil;
    View root;
    //Fisebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    String username = "";
    String email = "";

    private CuentaViewModel mViewModel;

    public static CuentaFragment newInstance() {
        return new CuentaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_cuenta, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Perfiles");
        perfil = root.findViewById(R.id.imgCuetaAdmin);
        nombre=root.findViewById(R.id.txtNombreUsuarioCuentaAdmin);
        correo=root.findViewById(R.id.txtEmailCuentaAdmin);
        databaseReference.child("Usuarios/Tienda").orderByChild("email").equalTo("admin@perroestilo.com.mx").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                Usuarios u=null;
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    u = objSnapshot.getValue(Usuarios.class);
                }
                if (u!=null){
                    nombre.setText(u.getUsername());
                    correo.setText(u.getEmail());
                    //perfil.setText(u.getFotoPerfil());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        siguiente=root.findViewById(R.id.imgbtnSiguienteCuentaAdmin);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.programacion.perroestilocliente.ui.administrador.cuenta.CuentaFragment cf= new com.programacion.perroestilocliente.ui.administrador.cuenta.CuentaFragment();
                Bundle args = new Bundle();
                cf.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, cf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return root;
        }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CuentaViewModel.class);
        // TODO: Use the ViewModel
    }

}