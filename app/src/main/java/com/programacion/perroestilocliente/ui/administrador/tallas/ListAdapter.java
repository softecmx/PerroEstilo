package com.programacion.perroestilocliente.ui.administrador.tallas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Tallas;

import java.util.ArrayList;
import java.util.UUID;

public class ListAdapter extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;
    LayoutInflater layoutInflater;
    Context context;
    View root;
    private androidx.appcompat.app.AlertDialog dialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ListAdapter(Activity activity, ArrayList<ElementListView> arrayList, LayoutInflater layoutInflater, Context context, View root) {
        super(activity, R.layout.item_lista_tallas, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        this.layoutInflater = layoutInflater;
        this.context = context;
        this.root = root;
        iniciaFirebase();
    }

    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_tallas, parent, false);
        }

        Button btnLisElim = convertView.findViewById(R.id.ibtnListaTallaElimina);
        Button btnLisModif = convertView.findViewById(R.id.ibtnListaTallaEdita);
        TextView txtCampo = convertView.findViewById(R.id.txtNombreTalla);
        TextView txtInformacion = convertView.findViewById(R.id.txtMedidasTalla);

        txtCampo.setText(arrayList.get(position).getTalla());
        txtInformacion.setText("Medidas : " + arrayList.get(position).getMedidas());

        btnLisElim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLisModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    public void vistaEditar(int position) {
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_agregar_veterinario, null);

        Button btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnTallaCancelar);
        Button btnPopAgregar = (Button) aboutPop.findViewById(R.id.btnAddTalla);
        TextView etTalla = (EditText) aboutPop.findViewById(R.id.etATtalla);
        TextView etMedidas = (EditText) aboutPop.findViewById(R.id.etATmedidas);

        etTalla.setText(arrayList.get(position).getTalla());
        etMedidas.setText(arrayList.get(position).getMedidas());

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnPopCerrar.setOnClickListener(view -> dialog.dismiss());
        btnPopAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String talla = (etTalla).getText().toString();
                String medida = (etMedidas).getText().toString();
                if (talla.equals("") || medida.equals("")) {
                    CustomToast.mostarToast("Faltan datos", 2, false, root, layoutInflater, context);
                } else {
                    Tallas tal = new Tallas();
                    tal.setTallas(talla);
                    tal.setMedidas(medida);
                    tal.setEstadoLogico("1");
                    databaseReference.child("Tallas").child(arrayList.get(position).getId()).setValue(tal);
                    CustomToast.mostarToast("Datos registrado!", 1, false, root, layoutInflater, context);
                    etTalla.setText("");
                    etMedidas.setText("");
                    dialog.dismiss();
                }
            }
        });
    }

    public void vistaEliminar(int position){
        databaseReference.child("Tallas").child(arrayList.get(position).getId()).removeValue();
    }

}
