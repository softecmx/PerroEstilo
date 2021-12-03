package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

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
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;

import java.util.ArrayList;

public class ListAdapterConsultarPagosinterna extends ArrayAdapter<ElementListViewPagosPendientes> {

    private Activity activity;
    private ArrayList<ElementListViewPagosPendientes> arrayList;
    private StorageReference storageReference;
    private Context context;

    public ListAdapterConsultarPagosinterna(Activity activity, ArrayList<ElementListViewPagosPendientes> arrayList, Context context) {
        super(activity, R.layout.item_lista_detalle_venta, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        iniciaFirebase();
    }
    public void iniciaFirebase(){
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_detalle_venta, parent, false);
        }

        TextView txtNombrepro= convertView.findViewById(R.id.txtNombreProductoDetVenta);
        TextView txtUnitario = convertView.findViewById(R.id.txtunitariodetalleventa);
        TextView txtCantidad= convertView.findViewById(R.id.txtCantidadProductoDetVenta);
        TextView txtSubtotal= convertView.findViewById(R.id.txtSubtotalProductoDetVenta);
        ImageView imgFoto= convertView.findViewById(R.id.imgDetalleVenta);

        txtNombrepro.setText("Producto: "+arrayList.get(position).getProducto());
        txtUnitario.setText("$"+arrayList.get(position).getUnitario());
        txtCantidad.setText("Cantidad: "+arrayList.get(position).getCantidad());
        txtSubtotal.setText("Subtotal: $"+arrayList.get(position).getSubtotal());
        cargaImagen(imgFoto,arrayList.get(position).getImgProducto());

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
                Toast.makeText(context,"Ups! Ha ocurrido un erro al recuperar la imagen\n" + e.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
