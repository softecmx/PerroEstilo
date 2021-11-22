package com.programacion.perroestilocliente.ui.cliente.productos.todosLosProductos;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.DataObjectHolder> {

    private Context context;
    private ArrayList<Producto> listaProductos;

    public RViewAdapter(Context context, ArrayList<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
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
                Toast.makeText(v.getContext(), "Position: " +
                        holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
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
