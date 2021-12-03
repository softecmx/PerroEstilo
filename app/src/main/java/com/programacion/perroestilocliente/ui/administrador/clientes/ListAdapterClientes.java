package com.programacion.perroestilocliente.ui.administrador.clientes;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class ListAdapterClientes extends ArrayAdapter<ElementListViewClientes> {

    private Activity activity;
    private ArrayList<ElementListViewClientes> arrayList;
    private StorageReference storageReference;

    public ListAdapterClientes(Activity activity, ArrayList<ElementListViewClientes> arrayList) {
        super(activity, R.layout.item_lista_clientes_lealtad, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        iniciaFirebase();
    }
    public void iniciaFirebase(){
        storageReference = FirebaseStorage.getInstance().getReference("Perfiles");
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_clientes_lealtad, parent, false);
        }

        TextView txtNombre= convertView.findViewById(R.id.txtNombreUsuarioLealtadLista);
        TextView txtStatus = convertView.findViewById(R.id.txtStatusUsuarioLealtad);
        ImageView imgFoto= convertView.findViewById(R.id.ivFotoClienteLealtad);

        txtNombre.setText(arrayList.get(position).getNombre());
        txtStatus.setText("Estatus : "+arrayList.get(position).getStatus());
        cargaImagen(imgFoto,arrayList.get(position).getFoto());

        return convertView;
    }
    private void cargaImagen(ImageView ivFoto, String img) {
        storageReference.child(img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(ivFoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Ups! Ha ocurrido un erro al recuperar la imagen\n" + e.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
