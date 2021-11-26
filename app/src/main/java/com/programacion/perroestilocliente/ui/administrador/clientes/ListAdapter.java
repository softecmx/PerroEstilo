package com.programacion.perroestilocliente.ui.administrador.clientes;

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

public class ListAdapter extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;

    public ListAdapter(Activity activity, ArrayList<ElementListView> arrayList) {
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
                            .inflate(R.layout.item_lista_clientes_lealtad, parent, false);
        }

        TextView txtNombre= convertView.findViewById(R.id.txtNombreUsuarioLealtadLista);
        TextView txtStatus = convertView.findViewById(R.id.txtStatusUsuarioLealtad);

        txtNombre.setText(arrayList.get(position).getNombre());
        txtStatus.setText("Estatus : "+arrayList.get(position).getStatus());

        return convertView;
    }


}
