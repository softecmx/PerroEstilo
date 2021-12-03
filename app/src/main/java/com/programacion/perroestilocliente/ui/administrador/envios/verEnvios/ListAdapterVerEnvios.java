package com.programacion.perroestilocliente.ui.administrador.envios.verEnvios;

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

public class ListAdapterVerEnvios extends ArrayAdapter<ElementListViewVerEnvios> {

    private Activity activity;
    private ArrayList<ElementListViewVerEnvios> arrayList;

    public ListAdapterVerEnvios(Activity activity, ArrayList<ElementListViewVerEnvios> arrayList) {
        super(activity, R.layout.item_lista_envios, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_envios, parent, false);
        }

        TextView txtOrden= convertView.findViewById(R.id.txtOrdenEnvio);
        TextView txtStatus = convertView.findViewById(R.id.txtStatusEnvio);
        TextView txtTotal= convertView.findViewById(R.id.txtTotalEnvio);
      //  TextView txtFecha= convertView.findViewById(R.id.txtFechaLlegadaEnvio);

        txtOrden.setText(arrayList.get(position).getOrdenPedido());
        txtStatus.setText(arrayList.get(position).getStatusPedido());
        txtTotal.setText(arrayList.get(position).getTotalPedido());
        //txtFecha.setText(arrayList.get(position).getFecha());

        return convertView;
    }


}
