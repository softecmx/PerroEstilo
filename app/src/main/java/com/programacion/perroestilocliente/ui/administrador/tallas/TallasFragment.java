package com.programacion.perroestilocliente.ui.administrador.tallas;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Tallas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class TallasFragment extends Fragment{

    private TallasViewModel mViewModel;

    private Button btnPopCerrar;
    private Button btnPopAgregar;

    private EditText etTalla;
    private EditText etMedidas;
    View root;
    private ListView listView;
    private ListAdapter customAdapter;
    com.google.android.material.floatingactionbutton.FloatingActionButton fBtnAgregar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Tallas tallaSelected;

    private ArrayList<Tallas> ListaTallas = new ArrayList<Tallas>();

    public static TallasFragment newInstance() {
        return new TallasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_tallas, container, false);

        // Inflate the layout for this fragment
        this.listView = root.findViewById(R.id.listTallas);
        this.fBtnAgregar=root.findViewById(R.id.fButtonAddTallas);
        iniciaFirebase();
        listarDatos();

        fBtnAgregar.setOnClickListener(view -> createDialogAgregar());

        return root;
    }
    public void listarDatos(){
        databaseReference.child("Tallas").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListView> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Tallas p = objSnapshot.getValue(Tallas.class);
                    arrayList.add(new ElementListView(p.getTallas(),p.getMedidas(),p.getIdTalla(), p.getEstadoLogico()));
                    customAdapter = new ListAdapter(getActivity(), arrayList,getLayoutInflater(),getContext(),root);
                    listView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TallasViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id=v.getId();


        MenuInflater inflater2= getActivity().getMenuInflater();
        if (id == R.id.listTallas) {
            inflater2.inflate(R.menu.menu_contextual_tallas, menu);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.menu_editar:
                createDialogEditar("1");
                //mostarToast("Editar |"+ info.position+" |  "+item.getTitle(),0,true);
                ElementListView elemento= (ElementListView) this.listView.getItemAtPosition(info.position);
                System.out.println("Editar: "+elemento.getTalla());
                return  true;
            case R.id.menu_consultar:
                createDialogInformacion("1");
                //mostarToast("Consultar |"+ info.position+" |  "+item.getTitle(),0,true);
                ElementListView elemento2= (ElementListView) this.listView.getItemAtPosition(info.position);
                System.out.println("Info: "+elemento2.getTalla());
                return  true;
            case R.id.menu_eliminar:
                createDialogEliminar("1");
                ElementListView elemento3= (ElementListView) this.listView.getItemAtPosition(info.position);
                System.out.println("Eliminar: "+elemento3.getTalla());
                //mostarToast("Eliminar |"+ info.position+" |  "+item.getTitle(),0,true);
                return  true;

        }
        return super.onContextItemSelected(item);
    }
    private androidx.appcompat.app.AlertDialog dialog;

    private void createDialogAgregar() {
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = getLayoutInflater().inflate(R.layout.dialog_agregar_veterinario, null);

        btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnTallaCancelar);
        btnPopAgregar = (Button) aboutPop.findViewById(R.id.btnAddTalla);
        etTalla = (EditText) aboutPop.findViewById(R.id.etATtalla);
        etMedidas = (EditText) aboutPop.findViewById(R.id.etATmedidas);

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnPopCerrar.setOnClickListener(view -> dialog.dismiss());
        btnPopAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String talla = (etTalla).getText().toString();
                String medida = (etMedidas).getText().toString();
                if (talla.equals("") || medida.equals("")){
                    mostarToast("Faltan datos",2,false);
                }else{
                    Tallas tal = new Tallas();
                    tal.setTallas(talla);
                    tal.setMedidas(medida);
                    tal.setEstadoLogico("1");
                    tal.setIdTalla(UUID.randomUUID().toString());
                    databaseReference.child("Tallas").child(tal.getIdTalla()).setValue(tal);
                    mostarToast("Datos registrado!",1,false);
                    etTalla.setText("");
                    etMedidas.setText("");
                    dialog.dismiss();
                }
            }
        });
    }

    private void createDialogEditar(String veterinario) {

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        // final View aboutPop = getLayoutInflater().inflate(R.layout.dialog_editar_veterinario, null);
        // btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnEditCancelar);


        //    dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnPopCerrar.setOnClickListener(view -> dialog.dismiss());
    }
    private void createDialogEliminar(String veterinario) {

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        //final View aboutPop = getLayoutInflater().inflate(R.layout.dialog_eliminar_veterinario, null);
        //btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnDelCancelar);

        // dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnPopCerrar.setOnClickListener(view -> dialog.dismiss());
    }
    private void createDialogInformacion(String veterinario) {

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        //    final View aboutPop = getLayoutInflater().inflate(R.layout.dialog_consultar_veterinario, null);
        //   btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnReadAceptar);


        //  dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnPopCerrar.setOnClickListener(view -> dialog.dismiss());
    }

    public void mostarToast(String txt, int estatus, boolean corto) {
        LayoutInflater inflater = getLayoutInflater();

        View layout;

        if(estatus==0){
            //Default
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) root.findViewById(R.id.layout_base));

        }else if(estatus==1){
            //Success
            layout = inflater.inflate(R.layout.custom_toast_success,
                    (ViewGroup) root.findViewById(R.id.layout_base));

        }else if(estatus==2) {
            //Warning
            layout = inflater.inflate(R.layout.custom_toast_warning,
                    (ViewGroup) root.findViewById(R.id.layout_base));

        }else if(estatus==3){
            //Error
            layout = inflater.inflate(R.layout.custom_toast_error,
                    (ViewGroup) root.findViewById(R.id.layout_base));


        }else if(estatus==4){
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_red,
                    (ViewGroup) root.findViewById(R.id.layout_base));


        }else if(estatus==5){
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_sin_data,
                    (ViewGroup) root.findViewById(R.id.layout_base));


        }else{
            //Informacion
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) root.findViewById(R.id.layout_base));
        }

        TextView textView = layout.findViewById(R.id.txtToast);
        textView.setText(txt);
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 50);
        if(corto){
            toast.setDuration(Toast.LENGTH_SHORT);}else{
            toast.setDuration(Toast.LENGTH_LONG);}
        toast.setView(layout);
        toast.show();
    }
}