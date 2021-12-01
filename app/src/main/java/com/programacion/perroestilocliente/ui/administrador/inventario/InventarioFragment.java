package com.programacion.perroestilocliente.ui.administrador.inventario;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.catalogo.SpnAdapterCategoria;
import com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventarioFragment extends Fragment implements AdapterView.OnItemClickListener{

    private TextView txtCodigo, txtProducto, txtModelo, txtTalla, txtStock,txtStatus;
    private Spinner spnStatus;
    private ImageButton imgbtnMas, imgbtnMenos;
    private Button btnAceptar, btnCancelar;
    private InventarioViewModel mViewModel;
    private ImageButton btnBuscar;

    private SpnAdapterEstadoStock spnEstado;

    View root;
    private ListView listView;
    private ListAdapterInventario customAdapter;
    private androidx.appcompat.app.AlertDialog dialog;
    private AutoCompleteTextView spnModStatus;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<Productos> ListaProductos = new ArrayList<Productos>();
    String disenio = "";
    String tallas = "";

    public static InventarioFragment newInstance() {
        return new InventarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inventario, container, false);


        listView = root.findViewById(R.id.listInventario);
        btnBuscar = root.findViewById(R.id.ibtnBuscarProductosInventario);
        imgbtnMas = root.findViewById(R.id.imgbtnMasStock);
        imgbtnMenos = root.findViewById(R.id.imgbtnMenosStock);
        iniciaFirebase();
        listarDatos();
        modificarStock();
        registerForContextMenu(listView);

        return root;
    }

    public void listarDatos() {

        databaseReference.child("Productos").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewInventario> arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Productos p = objSnapshot.getValue(Productos.class);
                    databaseReference.child("Disenios").orderByChild("idModelo").equalTo(p.getDisenio()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {

                                Disenios d = objSnapshot.getValue(Disenios.class);
                                disenio = d.getDisenio();

                                databaseReference.child("Tallas").orderByChild("idTalla").equalTo(p.getTalla()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                            try {
                                                Tallas t = objSnapshot.getValue(Tallas.class);
                                                tallas = t.getTallas();
                                                arrayList.add(new ElementListViewInventario(p.getIdProducto() ,p.getNombre(), disenio, tallas, p.getEstatusStock(), p.getStock()));
                                                customAdapter = new ListAdapterInventario(getActivity(), arrayList);
                                                listView.setAdapter(customAdapter);
                                            }catch (Exception e){

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void llenarSpn(){
        ArrayAdapter<CharSequence> generoAdapter;
        generoAdapter= ArrayAdapter.createFromResource(getContext(),R.array.estatus_producto, android.R.layout.simple_spinner_item);
        spnModStatus.setAdapter(generoAdapter);
        spnModStatus.setOnItemClickListener(this);
    }
    private void modificarStock() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ElementListViewInventario vistaElement= customAdapter.getItem(position);

                androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                final View aboutPop = getLayoutInflater().inflate(R.layout.dialogo_modificar_stock, null);

                btnAceptar = (Button) aboutPop.findViewById(R.id.btnAceptarCambioStock);
                btnCancelar = (Button) aboutPop.findViewById(R.id.btnCancelarCambioStock);
                imgbtnMenos =(ImageButton) aboutPop.findViewById(R.id.imgbtnMenosStock);
                imgbtnMas=(ImageButton) aboutPop.findViewById(R.id.imgbtnMasStock);
                txtCodigo = (TextView) aboutPop.findViewById(R.id.txtCodigoModificarStock);
                txtProducto= (TextView) aboutPop.findViewById(R.id.txtProductoModificarStock);
                txtModelo= (TextView) aboutPop.findViewById(R.id.txtModeloModificarStock);
                txtTalla= (TextView) aboutPop.findViewById(R.id.txtTallaModificarStock);
                spnModStatus=aboutPop.findViewById(R.id.spnStatusModificarStock);
                llenarSpn();
                txtStock= (TextView) aboutPop.findViewById(R.id.txtCantidadStock);
               // ArrayList<String> statusAdapter;
               // statusAdapter=ArrayAdapter.createFromResource(getContext(),R.array.Status, android.R.layout.simple_spinner_item);
               // spnEstado= new SpnAdapterEstadoStock(getActivity(),statusAdapter);

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialogBuilder.setView(aboutPop);
                dialog = dialogBuilder.create();
                txtCodigo.setText(vistaElement.getCodigo());
                txtProducto.setText(vistaElement.getProducto());
                txtModelo.setText(vistaElement.getModelo());
                txtTalla.setText(vistaElement.getTalla());
                spnModStatus.setText(vistaElement.getStatus());

                llenarSpn();
                txtStock.setText(vistaElement.getStock());

                imgbtnMenos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restarStock();
                    }
                });
                imgbtnMas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sumarStock();
                    }
                });


                dialog.show();
            }
        });


    }
    private void sumarStock(){
        int valorstokres=Integer.parseInt(txtStock.getText().toString()) +1;
        txtStock.setText(valorstokres + "");
            if (Integer.parseInt(txtStock.getText().toString())==1){
                spnModStatus.setText("Pocas existencias");
            } else {
                    spnModStatus.setText("En existencias");
            }

    }
    private void restarStock(){
        if(Integer.parseInt(txtStock.getText().toString())>0)
        {
            int valorstokres=Integer.parseInt(txtStock.getText().toString()) -1;
            txtStock.setText(valorstokres + "");
            if (Integer.parseInt(txtStock.getText().toString())==1){
                spnModStatus.setText("Pocas existencias");
            }else if (Integer.parseInt(txtStock.getText().toString())==0){
                //spnModStatus.setSelection(obtenerPosicion(spnstatus,sts));
                spnModStatus.setText("Agotado");
            }
        }else if (Integer.parseInt(txtStock.getText().toString())==0){
            //spnModStatus.setSelection(obtenerPosicion(spnstatus,sts));
            spnModStatus.setText("Agotado");
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InventarioViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}