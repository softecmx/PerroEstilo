package com.programacion.perroestilocliente.ui.administrador.tallas;

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
        super(activity, R.layout.item_lista_tallas, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_tallas, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtNombreTalla);
        TextView txtInformacion = convertView.findViewById(R.id.txtMedidasTalla);

        txtCampo.setText(arrayList.get(position).getTalla());
        txtInformacion.setText("Ced. Prof. : "+arrayList.get(position).getMedidas());

        return convertView;
    }


}
