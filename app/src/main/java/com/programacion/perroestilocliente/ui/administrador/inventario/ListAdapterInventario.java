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

import java.util.ArrayList;

public class ListAdapterInventario extends ArrayAdapter<ElementListViewInventario> {

    private Activity activity;
    private ArrayList<ElementListViewInventario> arrayList;

    public ListAdapterInventario(Activity activity, ArrayList<ElementListViewInventario> arrayList) {
        super(activity, R.layout.item_lista_inventario, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_inventario, parent, false);
        }
        TextView txtCodigo= convertView.findViewById(R.id.txtCodigoInnevtario);
        TextView txtProducto = convertView.findViewById(R.id.txtProductoInnevtario);
        TextView txtModelo = convertView.findViewById(R.id.txtModeloInnevtario);
        TextView txtTalla = convertView.findViewById(R.id.txtTallaInnevtario);
        TextView txtStatus = convertView.findViewById(R.id.txtEstatusInnevtario);
        TextView txtStock = convertView.findViewById(R.id.txtStockInnevtario);

        txtCodigo.setText(arrayList.get(position).getCodigo());
        txtProducto.setText(arrayList.get(position).getProducto());
        txtModelo.setText("Modelo: "+arrayList.get(position).getModelo());
        txtTalla.setText("Talla: "+arrayList.get(position).getTalla());
        txtStatus.setText(arrayList.get(position).getStatus());
        txtStock.setText(arrayList.get(position).getStock());

        return convertView;
    }


}














