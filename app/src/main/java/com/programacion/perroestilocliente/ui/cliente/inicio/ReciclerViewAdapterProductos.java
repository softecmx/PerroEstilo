package com.programacion.perroestilocliente.ui.cliente.inicio;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda.VerProductoTiendaFragment;

import java.util.ArrayList;

public class ReciclerViewAdapterProductos extends RecyclerView.Adapter<ReciclerViewAdapterProductos.DataObjectHolder> {

    private Context context;
    FragmentManager fragmentManager;
    private ArrayList<Productos> listaProductos;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");

    public ReciclerViewAdapterProductos(Context context, ArrayList<Productos> listaProductos, FragmentManager fragmentManager) {
        this.context = context;
        this.listaProductos = listaProductos;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_producto_tienda, viewGroup, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {

        holder.txtNombre.setText(listaProductos.get(position).getNombre());

        String raiting = listaProductos.get(position).getRaiting();
        float raitingStar = 0;
        if (raiting.isEmpty()) {
            raitingStar = 0;
        } else {
            raitingStar = Float.parseFloat(raiting);
        }
        holder.ratingBar.setRating(raitingStar);

        if (listaProductos.get(position).getDescuento().equals("") || listaProductos.get(position).getDescuento().equals("0")) {
            holder.txtDescuento.setVisibility(View.GONE);
            holder.txtOferta.setVisibility(View.GONE);
            holder.txtPrecio.setText("$" + listaProductos.get(position).getPrecioVenta());
        } else {
            holder.txtOferta.setVisibility(View.VISIBLE);
            holder.txtDescuento.setVisibility(View.VISIBLE);
            float descuento=Float.parseFloat(listaProductos.get(position).getDescuento());

            float precio=Float.parseFloat(listaProductos.get(position).getPrecioVenta());
            float descuentoReal=(descuento*precio)/100;
            float precioActual=precio-descuentoReal;
            float redondeo=Math.round(precioActual);
            holder.txtDescuento.setPaintFlags(holder.txtDescuento.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtDescuento.setText("$" + listaProductos.get(position).getPrecioVenta());
            holder.txtPrecio.setText("$" + redondeo);
        }

        //  Glide.with(context).load(listaProductos.get(position).getImgFoto()).into(holder.img);
        storageReference.child(listaProductos.get(position).getImgFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Position: " +
                        holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerProductoTiendaFragment newFragment1= new VerProductoTiendaFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtNombre;
        private TextView txtDescuento;
        private TextView txtOferta;
        private TextView txtPrecio;
        private RatingBar ratingBar;
        private Button btnVer;
        private Button btnAgregarCarrito;


        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.imgCardProductoTienda);
            this.txtNombre = itemView.findViewById(R.id.txtCardNombreProductoTienda);
            this.txtDescuento = itemView.findViewById(R.id.txtDescuentoProdTienda);
            this.txtOferta = itemView.findViewById(R.id.txtCardOferta);
            this.txtPrecio = itemView.findViewById(R.id.txtCardProdPrecio);
            this.ratingBar = itemView.findViewById(R.id.raitingBarProductoTienda);
            this.btnVer = itemView.findViewById(R.id.btnCardVerProd);
            this.btnAgregarCarrito = itemView.findViewById(R.id.btnCardAgregarProd);
        }
    }

}
