package com.programacion.perroestilocliente.ui.cliente.direcciones.misDirecciones;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Direcciones;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Direcciones> {

    private Activity activity;
    private ArrayList<Direcciones> arrayList;

    public ListAdapter(Activity activity, ArrayList<Direcciones> arrayList) {
        super(activity, R.layout.item_lista_direcciones, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_direcciones, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtDireccion);
        String direccion=arrayList.get(position).getEntidadFederativa()+", "+arrayList.get(position).getMunicipio()
                +", "+arrayList.get(position).getLocalidad()
                +". Calle Ext: "+arrayList.get(position).getCalleYNumeroExterno()+" y Calle Int: "
                +arrayList.get(position).getCalleYNumeroInterno();
        txtCampo.setText(direccion);

        return convertView;
    }


}
