package com.programacion.perroestilocliente.ui.cliente.inicio;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;

import java.util.List;

public class ListAdapterCategorias extends RecyclerView.Adapter<ListAdapterCategorias.ViewHolder> {

    private List<Categorias> mData;
    private LayoutInflater mInflater;
    private Context context;


    View v;

    public ListAdapterCategorias(List<Categorias> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = mInflater.inflate(R.layout.cardview_item_categorias, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
       // holder.cv.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  System.out.println("Hola");


            }
        });
    }


    public void setItems(List<Categorias> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNombre;
        ImageView itemImagen;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNombre = itemView.findViewById(R.id.txtCardCategoriaNombre);
            itemImagen=itemView.findViewById(R.id.imgViewCardCategoria);
            cv=itemView.findViewById(R.id.cardCategoriaCatalogo);
        }

        void bindData(final Categorias item) {
            itemNombre.setText(item.getNombreCategoria().toUpperCase());
        }

    }
}