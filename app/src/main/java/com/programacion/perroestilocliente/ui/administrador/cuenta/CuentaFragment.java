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
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.modelo.Usuarios;
import com.programacion.perroestilocliente.ui.administrador.perfil.PerfilAdministradorFragment;
import com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapter;

import java.util.ArrayList;


public class CuentaFragment extends Fragment {

    private TextView nombre,correo;
    ImageButton siguiente;
    private ImageView perfil;
    LinearLayout remplazar;
    View root;
    ListView lista_opciones;
    private ListAdapter customAdapter;
    //Fisebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private CuentaViewModel mViewModel;
    private ArrayList<Opciones> ListaOpciones = new ArrayList<Opciones>();
    public static CuentaFragment newInstance() {
        return new CuentaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_cuenta, container, false);
        siguiente=vista.findViewById(R.id.imgbtnSiguienteCuentaAdmin);
        //remplazar= vista.findViewById(R.id.llAdminCuenta);

        //this.lista_opciones=root.findViewById(R.id.listOpciones);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Perfiles");
        perfil = vista.findViewById(R.id.imgCuetaAdmin);
        nombre=vista.findViewById(R.id.txtNombreUsuarioCuentaAdmin);
        correo=vista.findViewById(R.id.txtEmailCuentaAdmin);
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

        return vista;
        }
    public void listarDatos(){
        Opciones opc= new Opciones();
        ArrayList<ElementListView> arrayList = new ArrayList<>();
//                    arrayList.add(new ElementListViewPagosPendientes(opc.getInfo(),opc.getAyuda(),opc.getApp(),opc.getPoliticas(),opc.getAjustes(),opc.getCerrar()));
                    //customAdapter = new ListAdapter(getActivity(), arrayList);
                   // lista_opciones.setAdapter(customAdapter);

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