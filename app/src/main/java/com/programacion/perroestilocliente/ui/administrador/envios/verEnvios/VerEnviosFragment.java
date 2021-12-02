package com.programacion.perroestilocliente.ui.administrador.envios.verEnvios;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;

import java.util.ArrayList;

public class VerEnviosFragment extends Fragment {
    View root;
    ImageView imgbtnBuscar;
    private VerEnviosViewModel mViewModel;

    public static VerEnviosFragment newInstance() {
        return new VerEnviosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.fragment_ver_envios, container, false);
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        imgbtnBuscar=root.findViewById(R.id.ibtnBuscarPedidoEnvios);
        ListView reciclerViewEnvios = root.findViewById(R.id.listVerEnvios);
        ArrayList<Item> arrayListItems = new ArrayList<>();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerEnviosViewModel.class);
        // TODO: Use the ViewModel
    }

}