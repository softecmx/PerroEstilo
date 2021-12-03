package com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class ListAdapterConsultarPedidos extends ArrayAdapter<ElementListViewConsultarPedidos> {

    private Activity activity;
    private ArrayList<ElementListViewConsultarPedidos> arrayList;

    public ListAdapterConsultarPedidos(Activity activity, ArrayList<ElementListViewConsultarPedidos> arrayList) {
        super(activity, R.layout.item_lista_clientes_lealtad, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_pagos, parent, false);
        }

        TextView txtOrden = convertView.findViewById(R.id.txtOrdenPedido);
        TextView txtStatus = convertView.findViewById(R.id.txtStatusPedido);
        TextView txtTotal = convertView.findViewById(R.id.txtTotalPedido);

        txtOrden.setText("No. Orden: " + arrayList.get(position).getOrdenPedido());
        txtStatus.setText("" + arrayList.get(position).getStatusPedido());
        txtTotal.setText("Total: $" + arrayList.get(position).getTotalPedido());

        return convertView;
    }


}
