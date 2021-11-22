package com.programacion.perroestilocliente.ui.ajustes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;

    public ListAdapter(Activity activity, ArrayList<ElementListView> arrayList) {
        super(activity, R.layout.item_lista_ajustes, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_ajustes, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.txtNombreAjuste);
        TextView txtInformacion = convertView.findViewById(R.id.txtDescripcionAjuste);
        androidx.appcompat.widget.SwitchCompat switchCompat=convertView.findViewById(R.id.swicthItemLista);

        txtCampo.setText(arrayList.get(position).getAjuste());
        txtInformacion.setText(arrayList.get(position).getDescripcion());

        if(arrayList.get(position).isSwitchItem()){
            switchCompat.setVisibility(View.VISIBLE);
        }else{
            switchCompat.setVisibility(View.GONE);
        }
        return convertView;
    }


}
