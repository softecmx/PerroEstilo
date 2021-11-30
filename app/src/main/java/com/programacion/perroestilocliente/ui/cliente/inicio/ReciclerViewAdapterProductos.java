package com.programacion.perroestilocliente.ui.cliente.inicio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
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
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda.VerProductoTiendaFragment;

import java.util.ArrayList;

public class ReciclerViewAdapterProductos extends RecyclerView.Adapter<ReciclerViewAdapterProductos.DataObjectHolder> {

    final Context context;
    FragmentManager fragmentManager;
    final ArrayList<Productos> listaProductos;
    String id;
    final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");

    public ReciclerViewAdapterProductos(Context context, ArrayList<Productos> listaProductos, FragmentManager fragmentManager) {
        this.context = context;
        this.listaProductos = listaProductos;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_producto_tienda, viewGroup, false);
        return new DataObjectHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {

        holder.txtNombre.setText(listaProductos.get(position).getNombre());
        id = listaProductos.get(position).getIdProducto();
        System.out.println(listaProductos.get(position).getIdProducto() + " RECICLER VIEW");
        String raiting = listaProductos.get(position).getRaiting();
        float raitingStar;
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
            float descuento = Float.parseFloat(listaProductos.get(position).getDescuento());

            float precio = Float.parseFloat(listaProductos.get(position).getPrecioVenta());
            float descuentoReal = (descuento * precio) / 100;
            float precioActual = precio - descuentoReal;
            float redondeo = Math.round(precioActual);
            holder.txtDescuento.setPaintFlags(holder.txtDescuento.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtDescuento.setText("$" + listaProductos.get(position).getPrecioVenta());
            holder.txtPrecio.setText("$" + redondeo);
        }

        //  Glide.with(context).load(listaProductos.get(position).getImgFoto()).into(holder.img);
        storageReference.child(listaProductos.get(position).getImgFoto()).getDownloadUrl().addOnSuccessListener(uri -> Glide.with(context).load(uri).into(holder.img)).addOnFailureListener(e -> Toast.makeText(context, "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show());
        holder.btnAgregarCarrito.setOnClickListener(v -> {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            Query findCarrito = ref.child(String.format("Carrito/%s/Items", user.getUid())).orderByChild("idProducto").equalTo(id);
            findCarrito.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                    Item item = objSnapshot.getValue(Item.class);
                                    Item nvoItem = new Item();
                                    nvoItem.setIdUsuario(user.getUid());
                                    nvoItem.setProducto(id);

                                    assert item != null;
                                    nvoItem.setCantidad(item.getCantidad() + 1);
                                    System.out.println(ref);
                                    ref.child("Carrito/" + user.getUid() + "/Items").child(item.getIdUsuario()).setValue(nvoItem).addOnSuccessListener(aVoid -> {
                                    }).addOnFailureListener(e -> {
                                    });
                                }
                            } else {
                                Item item = new Item();
                                item.setProducto(id);
                                item.setIdUsuario(user.getUid());
                                item.setCantidad(1);

                                float descuento = Float.parseFloat(listaProductos.get(position).getDescuento());

                                float precio = Float.parseFloat(listaProductos.get(position).getPrecioVenta());
                                float descuentoReal = (descuento * precio) / 100;
                                float precioActual = precio - descuentoReal;
                                float redondeo = Math.round(precioActual);

                                item.setPrecio(redondeo);
                                item.setImg(listaProductos.get(position).getImgFoto());
                                ref.child("Carrito/" + user.getUid() + "/Items").child(item.getProducto()).setValue(item).addOnSuccessListener(aVoid -> {
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        });
        holder.btnVer.setOnClickListener(v -> {
            VerProductoTiendaFragment newFragment1 = new VerProductoTiendaFragment();
            Bundle args = new Bundle();
            args.putString("idProducto", id);
            newFragment1.setArguments(args);
            System.out.println(id + " HOME PRODUCTOS");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_cliente, newFragment1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static  class DataObjectHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        final TextView txtNombre;
        final TextView txtDescuento;
        final TextView txtOferta;
        final TextView txtPrecio;
        final RatingBar ratingBar;
        final Button btnVer;
        final Button btnAgregarCarrito;


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