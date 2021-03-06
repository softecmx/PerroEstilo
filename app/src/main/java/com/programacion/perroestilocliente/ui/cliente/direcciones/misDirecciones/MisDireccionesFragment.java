package com.programacion.perroestilocliente.ui.cliente.direcciones.misDirecciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Direcciones;
import com.programacion.perroestilocliente.ui.cliente.direcciones.agregarDireccion.AgregarDireccionFragment;
import com.programacion.perroestilocliente.ui.cliente.direcciones.verDireccion.VerDireccionFragment;

import java.util.ArrayList;

public class MisDireccionesFragment extends Fragment {

    private MisDireccionesViewModel mViewModel;
    com.google.android.material.floatingactionbutton.FloatingActionButton fbtnAddDireccion;
    ListView listMisDirecciones;
    private ListAdapter customAdapter;
    View root;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    ArrayList<Direcciones> arrayList = new ArrayList<>();

    public static MisDireccionesFragment newInstance() {
        return new MisDireccionesFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mis_direcciones, container, false);
        listMisDirecciones = root.findViewById(R.id.listMisDirecciones);
        fbtnAddDireccion = root.findViewById(R.id.fbtnAddDireccion);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        this.listMisDirecciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Direcciones direcciones= (Direcciones) listMisDirecciones.getItemAtPosition(i);
                VerDireccionFragment newFragment1 = new VerDireccionFragment();
                Bundle args = new Bundle();
                args.putString("idDireccion",direcciones.getIdDireccion());
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Query queryMascota = databaseReference.child("Direcciones").orderByChild("idUser").equalTo(user.getUid());
        queryMascota.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    if(getActivity()!=null){
                    Direcciones direccion = objSnapshot.getValue(Direcciones.class);
                    arrayList.add(direccion);
                    customAdapter = new ListAdapter(getActivity(), arrayList);
                    listMisDirecciones.setAdapter(customAdapter);
                    }else{
                        queryMascota.removeEventListener(this);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        fbtnAddDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AgregarDireccionFragment newFragment1 = new AgregarDireccionFragment();
                // Bundle args = new Bundle();
                // newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MisDireccionesViewModel.class);
        // TODO: Use the ViewModel
    }

}