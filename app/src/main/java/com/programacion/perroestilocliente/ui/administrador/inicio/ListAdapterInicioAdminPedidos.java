package com.programacion.perroestilocliente.ui.administrador.inicio;

import android.app.Activity;
import android.content.Context;
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

public class ListAdapterInicioAdminPedidos extends ArrayAdapter<ElementListViewInicioAdmin> {

    private Activity activity;
    private ArrayList<ElementListViewInicioAdmin> arrayList;
    private StorageReference storageReference;
    private Context context;
    String img="";



    public ListAdapterInicioAdminPedidos(Activity activity, ArrayList<ElementListViewInicioAdmin> arrayList, Context context) {
        super(activity, R.layout.fragment_lista_nuevos_pedidos_inicio_admin, arrayList);
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
                            .inflate(R.layout.fragment_lista_nuevos_pedidos_inicio_admin, parent, false);
        }
        TextView txtUsuario= convertView.findViewById(R.id.txtItemPedidoInicioAdminPedidos);
        ImageView imgFoto= convertView.findViewById(R.id.imgItemUsuarioFotoInicioAdminPedidos);

        txtUsuario.setText(arrayList.get(position).getUsuario());
    //    cargaImagen(imgFoto,arrayList.get(position).getImgUsuario());

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
