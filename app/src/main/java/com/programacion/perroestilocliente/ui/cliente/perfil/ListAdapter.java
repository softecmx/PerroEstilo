package com.programacion.perroestilocliente.ui.cliente.perfil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<com.programacion.perroestilocliente.ui.cliente.perfil.ElementListView> {

    private Activity activity;
    private ArrayList<com.programacion.perroestilocliente.ui.cliente.perfil.ElementListView> arrayList;

    public ListAdapter(Activity activity, ArrayList<com.programacion.perroestilocliente.ui.cliente.perfil.ElementListView> arrayList) {
        super(activity, R.layout.item_lista_cuenta, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_cuenta, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtCampo);
        TextView txtInformacion = convertView.findViewById(R.id.txtInformacion);
        ImageView imgIcono = convertView.findViewById(R.id.imgIcon);
        ImageView imgEdit = convertView.findViewById(R.id.imgEdit);

        imgIcono.setImageResource(arrayList.get(position).getImg());
        imgIcono.setColorFilter(R.color.pink_300);

        if(arrayList.get(position).isEditar()){
            imgEdit.setVisibility(View.VISIBLE);
        }else{
            imgEdit.setVisibility(View.GONE);
        }

        txtCampo.setText(arrayList.get(position).getNombre());
        txtInformacion.setText(arrayList.get(position).getInformacion());

        return convertView;
    }


}
