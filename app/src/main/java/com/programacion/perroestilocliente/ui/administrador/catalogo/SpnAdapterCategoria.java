package com.programacion.perroestilocliente.ui.administrador.catalogo;
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

import java.util.ArrayList;
public class SpnAdapterCategoria extends ArrayAdapter<ElementListView>  {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;

    public SpnAdapterCategoria(Activity activity, ArrayList<ElementListView> arrayList) {
        super(activity, R.layout.item_spn_categorias, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_spn_categorias, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtNombreCategoria);
        TextView txtInformacion = convertView.findViewById(R.id.txtDescCategoria);

        txtCampo.setText(arrayList.get(position).getNombre());
        txtInformacion.setText(arrayList.get(position).getTipo());

        return convertView;
    }
    public ElementListView getItem(int index) {
        return arrayList.get(index);
    }
}
