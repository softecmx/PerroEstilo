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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapterInicioAdminPedidos extends ArrayAdapter<ElementListViewInicioAdmin> {

    private Activity activity;
    private ArrayList<ElementListViewInicioAdmin> arrayList;
    private StorageReference storageReference;
    private Context context;
    String img = "";


    public ListAdapterInicioAdminPedidos(Activity activity, ArrayList<ElementListViewInicioAdmin> arrayList, Context context) {
        super(activity, R.layout.fragment_lista_nuevos_pedidos_inicio_admin, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        iniciaFirebase();
    }

    public void iniciaFirebase() {
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
        TextView txtUsuario = convertView.findViewById(R.id.txtItemPedidoInicioAdminPedidos);
        TextView txtFecha = convertView.findViewById(R.id.txtItemPedidoInicioAdminFecha);
        TextView txtNoOrden = convertView.findViewById(R.id.txtItemPedidoInicioAdminNoSerie);
        de.hdodenhof.circleimageview.CircleImageView imgFoto = convertView.findViewById(R.id.imgItemUsuarioFotoInicioAdminPedidos);
        txtFecha.setText(" " + arrayList.get(position).getFecha());
        txtNoOrden.setText("No. Orden: " + arrayList.get(position).getNoOrden());
        txtUsuario.setText(arrayList.get(position).getUsuario() + "");
        //  cargaImagen(imgFoto,arrayList.get(position).getImgUsuario());

        if (arrayList.get(position).getImgUsuario().contains("facebook")) {
            if (arrayList.get(position).getImgUsuario() != null) {

                //cImgFoto.setImageURI(user.getPhotoUrl());
                String pholoURL = arrayList.get(position).getImgUsuario().toString();
                pholoURL = pholoURL + "?type=large";
                Picasso.get().load(pholoURL)
                        .error(R.drawable.img_user_exist)
                        .into(imgFoto);
            } else {
                imgFoto.setImageResource(R.drawable.img_user_exist);
            }
        } else if (arrayList.get(position).getImgUsuario().contains("googleusercontent")) {
            if (arrayList.get(position).getImgUsuario() != null) {
                //cImgFoto.setImageURI(user.getPhotoUrl());
                Picasso.get().load(arrayList.get(position).getImgUsuario())
                        .error(R.drawable.img_user_exist)
                        .into(imgFoto);
            } else {
                imgFoto.setImageResource(R.drawable.img_user_exist);
            }
        } else if (!arrayList.get(position).getImgUsuario().isEmpty()) {
            if (activity != null) {
                storageReference.child(arrayList.get(position).getImgUsuario()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext()).load(uri).into(imgFoto);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        } else {
            imgFoto.setImageResource(R.drawable.img_user_exist);
        }
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
                Toast.makeText(getContext(), "Ups! Ha ocurrido un erro al recuperar la imagen\n" + e.getCause(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
