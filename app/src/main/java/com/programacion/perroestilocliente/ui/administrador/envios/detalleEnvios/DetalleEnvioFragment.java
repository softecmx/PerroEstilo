package com.programacion.perroestilocliente.ui.administrador.envios.detalleEnvios;

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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Usuarios;

public class DetalleEnvioFragment extends Fragment {
    Button btnConfirmarEntrega;
    Button btnContactarCliente;
    TextView status,fecha,orden,serie,fechaDetalle,nombreContacto,telContacto,direccionContacto;
    TextView total;
    ListView listView;
    View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private DetalleEnvioViewModel mViewModel;

    public static DetalleEnvioFragment newInstance() {
        return new DetalleEnvioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       root=inflater.inflate(R.layout.fragment_detalle_envio, container, false);
       btnConfirmarEntrega= root.findViewById(R.id.btnConfirmarDetalleEnvio);
       btnContactarCliente= root.findViewById(R.id.btnConfirmarDetalleEnvio);
        return root;
    }
    public void inicializarComponentes(){
        status= root.findViewById(R.id.txtStatusDetalleEnvio);
        fecha=root.findViewById(R.id.txtFechaDetalleEnvio);
        orden= root.findViewById(R.id.txtNoOrdenDetalleEnvio);
        serie= root.findViewById(R.id.txtNoSerieDetalleEnvio);
        fechaDetalle=root.findViewById(R.id.txtFechaEstimadaDetEnvio);
        nombreContacto=root.findViewById(R.id.txtContactoDetEnvio);
        telContacto=root.findViewById(R.id.txtTelefonoDetEnvio);
        direccionContacto= root.findViewById(R.id.txtDireccionDetEnvio);
        total=root.findViewById(R.id.txtTotalDetalleEnvio);
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
    public void botones(){
        if(btnContactarCliente.callOnClick()){
            btnContactarCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactarCliente();
                }
            });
        }
        if(btnConfirmarEntrega.callOnClick()){
            btnConfirmarEntrega.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Se ha realizado el envio del paquete", Toast.LENGTH_SHORT).show();
                }
            });
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
        mViewModel = new ViewModelProvider(this).get(DetalleEnvioViewModel.class);
        // TODO: Use the ViewModel
    }

}