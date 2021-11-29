package com.programacion.perroestilocliente.ui.cliente.mainCliente;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterCarrito extends ArrayAdapter<Item> {

    private Activity activity;
    private ArrayList<Item> arrayList;

    public ListAdapterCarrito(Activity activity, ArrayList<Item> arrayList) {
        super(activity, R.layout.item_prod_lista_carrito, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_prod_lista_carrito, parent, false);
        }


        return convertView;
    }
}