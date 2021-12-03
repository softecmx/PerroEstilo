package com.programacion.perroestilocliente.ui.administrador.ventas.verDetalleVenta;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Usuarios;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.ReciclerViewAdapterPedidos;

import java.util.ArrayList;

public class VerDetalleVentaFragment extends Fragment {

    private VerDetalleVentaViewModel mViewModel;
    Button btnContactarCliente;
    TextView status,fecha,orden,serie,fechaDetalle,nombreContacto,telContacto,direccionContacto;
    TextView total;
    ListView listView;
    View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    androidx.recyclerview.widget.RecyclerView reciclerViewOrdenes;
    ArrayList<OrdenesCliente> arrayListOrdenes = new ArrayList<>();
    private ReciclerViewAdapterPedidos adapterOrdenes;


    public static VerDetalleVentaFragment newInstance() {
        return new VerDetalleVentaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       root=inflater.inflate(R.layout.fragment_detalle_ventas, container, false);
       btnContactarCliente=root.findViewById(R.id.btnContactarDetalleVentas);
        btnContactarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactarCliente();
            }
        });


        return root;
    }
    public void inicializarComponentes(){
        status= root.findViewById(R.id.txtStatusDetVenta);
        fecha=root.findViewById(R.id.txtFechaDetVenta);
        orden= root.findViewById(R.id.txtNoOrdenDetVenta);
        serie= root.findViewById(R.id.txtNoSerieDetVenta);
        fechaDetalle=root.findViewById(R.id.txtFechaEstimadaDetVenta);
        nombreContacto=root.findViewById(R.id.txtContactoDetVenta);
        telContacto=root.findViewById(R.id.txtTelefonoDetVenta);
        direccionContacto= root.findViewById(R.id.txtDireccionDetVenta);
        total=root.findViewById(R.id.txtTotalDetVenta);
    }
    public void contactarCliente(){
        try {
            Usuarios u= new Usuarios();
            //u.getTelefono();
            //obtener telefono(del cliente) de la clase usuarios
            Intent setIntent=new Intent();
            setIntent.setAction(Intent.ACTION_VIEW);
            String uri="whatsapp://send?phone="+52+ u.getTelefono()+"&text="+"Hola, soy de Perro Estilo y necesito ...";
            setIntent.setData(Uri.parse(uri));
            startActivity(setIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();

            Snackbar.make(getView(), "El dispositivo no tiene instalado WhatsApp", Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerDetalleVentaViewModel.class);
        // TODO: Use the ViewModel
    }

}