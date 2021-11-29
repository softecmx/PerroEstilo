package com.programacion.perroestilocliente.ui.cliente.tienda;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.programacion.perroestilocliente.modelo.Productos;
import java.util.List;

public class ListAdapterProductosModa extends ArrayAdapter<Productos> {

    private Activity activity;
    private List<Productos> arrayList;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");

    public ListAdapterProductosModa(Activity activity, List<Productos> arrayList) {
        super(activity, R.layout.item_lista_tienda_producto, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_tienda_producto, parent, false);
        }
        ImageView imgItemProdFoto = convertView.findViewById(R.id.imgItemProdFoto);
        TextView txtItemProdNombre = convertView.findViewById(R.id.txtItemProdNombre);
        TextView txtItemProdPrecio = convertView.findViewById(R.id.txtItemProdPrecio);
        RatingBar raitingBarItemProd = convertView.findViewById(R.id.raitingBarItemProd);

        txtItemProdNombre.setText(arrayList.get(position).getNombre());

        txtItemProdPrecio.setText(arrayList.get(position).getPrecioVenta());

        String raiting = arrayList.get(position).getRaiting();
        float raitingStar = 0;
        if (raiting.isEmpty()) {
            raitingStar = 0;
        } else {
            raitingStar = Float.parseFloat(raiting);
        }
        raitingBarItemProd.setRating(raitingStar);

        if (arrayList.get(position).getDescuento().equals("") || arrayList.get(position).getDescuento().equals("0")) {

            txtItemProdPrecio.setText("$" + arrayList.get(position).getPrecioVenta());
        } else {
            float descuento = Float.parseFloat(arrayList.get(position).getDescuento());

            float precio = Float.parseFloat(arrayList.get(position).getPrecioVenta());
            float descuentoReal = (descuento * precio) / 100;
            float precioActual = precio - descuentoReal;
            float redondeo = Math.round(precioActual);
            txtItemProdPrecio.setText("$" + redondeo);
        }

        storageReference.child(arrayList.get(position).getImgFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(imgItemProdFoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
