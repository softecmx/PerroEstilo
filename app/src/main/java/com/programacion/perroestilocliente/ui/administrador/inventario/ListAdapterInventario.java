package com.programacion.perroestilocliente.ui.administrador.inventario;

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
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class ListAdapterInventario extends ArrayAdapter<ElementListViewInventario> {

    private Activity activity;
    private ArrayList<ElementListViewInventario> arrayList;

    public ListAdapterInventario(Activity activity, ArrayList<ElementListViewInventario> arrayList) {
        super(activity, R.layout.item_lista_inventario, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_inventario, parent, false);
        }
        TextView txtCodigo = convertView.findViewById(R.id.txtCodigoInnevtario);
        TextView txtProducto = convertView.findViewById(R.id.txtProductoInnevtario);
        TextView txtModelo = convertView.findViewById(R.id.txtModeloInnevtario);
        TextView txtTalla = convertView.findViewById(R.id.txtTallaInnevtario);
        TextView txtStatus = convertView.findViewById(R.id.txtEstatusInnevtario);
        TextView txtStock = convertView.findViewById(R.id.txtStockInnevtario);
//        ImageView imgProd = convertView.findViewById(R.id.imgItemInventario);
//
//        storageReference.child(arrayList.get(position).getImg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(getContext()).load(uri).into(imgProd);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
//            }
//        });

        txtCodigo.setText(arrayList.get(position).getCodigo());
        txtProducto.setText(arrayList.get(position).getProducto());
        txtModelo.setText("Modelo: " + arrayList.get(position).getModelo());
        txtTalla.setText("Talla: " + arrayList.get(position).getTalla());

        txtStock.setText("Stock: " + arrayList.get(position).getStock());

        if (arrayList.get(position).getStatus().equals("En existencias")) {
            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_awesome_green_1));
        } else if (arrayList.get(position).getStatus().equals("Pocas existencias")) {
            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_yellow_1));
        } else if (arrayList.get(position).getStatus().equals("Agotado")) {
            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.danger));
        } else if (arrayList.get(position).getStatus().equals("Descontinuado")) {
            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_grey_dark_1));
        } else {
            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_grey_dark_1));
        }
        txtStatus.setText(arrayList.get(position).getStatus());
        return convertView;
    }


}














