package com.programacion.perroestilocliente.ui.administrador.inventario;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.ui.administrador.catalogo.ElementListView;

import java.util.ArrayList;

public class SpnAdapterEstadoStock extends ArrayAdapter<String> {
    private Activity activity;
    private ArrayList<String> arrayList;

    public SpnAdapterEstadoStock(Activity activity, ArrayList<String> arrayList) {
        super(activity, R.layout.item_spn_estado_stock_inventario, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_spn_estado_stock_inventario, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtNombreEstadoStockIn);

        txtCampo.setText(arrayList.get(position));

        return convertView;
    }
}


