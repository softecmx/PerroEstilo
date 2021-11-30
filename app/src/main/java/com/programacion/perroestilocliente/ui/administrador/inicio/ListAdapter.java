package com.programacion.perroestilocliente.ui.administrador.inicio;

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
import com.programacion.perroestilocliente.ui.ajustes.ElementListView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;

    public ListAdapter(Activity activity, ArrayList<ElementListView> arrayList) {
        super(activity, R.layout.item_lista_opciones_admin_cuenta, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_opciones_admin_cuenta, parent, false);
        }
        //TextView txtNombre= convertView.findViewById(R.id.txtOpcionesAdminCuenta);
       // ImageView img= convertView.findViewById(R.id.imgImagenOpcionesAdminCuenta);

        //txtNombre.setText(arrayList.get(position).getDescripcion());
       // img.setImage(arrayList.get(position).getImg());

        return convertView;
    }


}
