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

public class ListAdapterSimple extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;

    public ListAdapterSimple(Activity activity, ArrayList<ElementListView> arrayList) {
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
                            .inflate(R.layout.item_simple_lista_tallas, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtNombreTallaSimple);
        TextView txtInformacion = convertView.findViewById(R.id.txtMedidasTallaSimple);

        txtCampo.setText(arrayList.get(position).getTalla());
        txtInformacion.setText("Medidas : "+arrayList.get(position).getMedidas());

        return convertView;
    }


}
