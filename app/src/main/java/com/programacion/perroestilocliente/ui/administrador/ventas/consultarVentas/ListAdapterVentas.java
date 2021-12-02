package com.programacion.perroestilocliente.ui.administrador.ventas.consultarVentas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.ui.administrador.inventario.ElementListViewInventario;

import java.util.ArrayList;

public class ListAdapterVentas extends ArrayAdapter<ElementListViewVentas> {

    private Activity activity;
    private ArrayList<ElementListViewVentas> arrayList;

    public ListAdapterVentas(Activity activity, ArrayList<ElementListViewVentas> arrayList) {
        super(activity, R.layout.item_lista_ventas_fechas, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_ventas_fechas, parent, false);
        }
        TextView txtFecha= convertView.findViewById(R.id.txtFechaVenta);
        TextView txtCantidad = convertView.findViewById(R.id.txtCantidadDeVentas);
        TextView txtTotal = convertView.findViewById(R.id.txtTotalVendido);

        txtFecha.setText(arrayList.get(position).getFecha());
        txtCantidad.setText(arrayList.get(position).getNoVentas());
        txtTotal.setText("Modelo: "+arrayList.get(position).getTotalVentas());

        return convertView;
    }


}














