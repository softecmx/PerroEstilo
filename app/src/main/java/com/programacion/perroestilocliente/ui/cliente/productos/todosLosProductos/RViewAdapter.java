package com.programacion.perroestilocliente.ui.cliente.productos.todosLosProductos;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;



import com.bumptech.glide.Glide;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda.VerProductoTiendaFragment;

import java.util.ArrayList;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.DataObjectHolder> {

    private Context context;
    private ArrayList<Producto> listaProductos;
    FragmentManager fragmentManager;
    FragmentContainerView container;
    public RViewAdapter(Context context, ArrayList<Producto> listaProductos, FragmentManager fragmentManager) {
        this.context = context;
        this.listaProductos = listaProductos;
        this.fragmentManager=fragmentManager;
        this.container=container;
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

        holder.txtNombre.setText(listaProductos.get(position).getNombreProducto());
        holder.txtDescuento.setPaintFlags(holder.txtDescuento.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context).load(listaProductos.get(position).getImg()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
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


        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.imgCardProductoTienda);
            this.txtNombre = itemView.findViewById(R.id.txtCardNombreProductoTienda);
            this.txtDescuento=itemView.findViewById(R.id.txtDescuentoProdTienda);
        }
    }

}
