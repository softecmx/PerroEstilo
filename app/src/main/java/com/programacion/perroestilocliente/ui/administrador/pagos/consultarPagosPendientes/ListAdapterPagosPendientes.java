package com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes;

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

public class ListAdapterPagosPendientes extends ArrayAdapter<ElementListViewPagosPendientes> {

    private Activity activity;
    private ArrayList<ElementListViewPagosPendientes> arrayList;

    public ListAdapterPagosPendientes(Activity activity, ArrayList<ElementListViewPagosPendientes> arrayList) {
        super(activity, R.layout.item_lista_pagos, arrayList);
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
        TextView txtOrden= convertView.findViewById(R.id.txtOrdenPago);
        TextView txtTotal = convertView.findViewById(R.id.txtTotalPago);
        TextView txtStatus = convertView.findViewById(R.id.txtStatusPago);

        txtOrden.setText(arrayList.get(position).getOrden());
        txtTotal.setText("$"+arrayList.get(position).getTotal());
        txtStatus.setText(arrayList.get(position).getStatusPago());

        return convertView;
    }


}














