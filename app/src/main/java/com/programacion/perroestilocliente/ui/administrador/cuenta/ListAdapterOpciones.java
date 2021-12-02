package com.programacion.perroestilocliente.ui.administrador.cuenta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

import java.util.List;

public class ListAdapterOpciones {
    protected Activity activity;
    protected List<Opciones> item;

    public ListAdapterOpciones(Activity activity, List<Opciones> item) {
        this.activity = activity;
        this.item = item;
    }

    public void clear() {
        item.clear();
    }

    // @Override
    public int getCount() {
        return item.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.fragment_cuenta, null);
        }
        //item.add("Acerca de nosotros");
        return v;
    }
}
