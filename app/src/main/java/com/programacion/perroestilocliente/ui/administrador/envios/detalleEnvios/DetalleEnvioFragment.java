package com.programacion.perroestilocliente.ui.administrador.envios.detalleEnvios;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.DetOrdenProductos;
import com.programacion.perroestilocliente.modelo.Direcciones;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Usuarios;
import com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.ElementListViewVerEnvios;
import com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.ListAdapterVerEnvios;
import com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.ListAdapterVerEnviosInterna;
import com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.VerEnviosFragment;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ConsultarPedidosFragment;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ListAdapterConsultarPedidosinterna;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetalleEnvioFragment extends Fragment {
    Button btnConfirmarEntrega;
    Button btnContactarCliente;
    TextView txtStatus,txtFecha,txtOrden,txtSerie,txtFechaDetalle,txtNombreContacto,txtTelContacto,txtDireccionContacto;
    TextView txtTotal;
    View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String idOrden;
    String status = "";
    String fechaEst;
    String fechaEntrega;
    float total;
    String idCliente;
    String idordengt, statusgt, idusuario;
    float totalgt;
    ListView listdetalles;

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

        Bundle args = getArguments();
        idOrden = args.getString("idOrden");
        idusuario = args.getString("idusuario");
        statusgt = args.getString("status");
        totalgt = args.getFloat("total");
       inicializarComponentes();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listdetalles = root.findViewById(R.id.lstViewDetalleCompraDetEnvio);
        ArrayList<ElementListViewVerEnvios> arrayList = new ArrayList<>();
        total = 0;

        databaseReference.child("OrdenesCliente/" + idusuario)
                .orderByChild("inOrden").equalTo(idOrden)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayList.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                OrdenesCliente ordenesCliente = objSnapshot.getValue(OrdenesCliente.class);
                                txtOrden.setText(ordenesCliente.getInOrden());
                                txtSerie.setText(ordenesCliente.getNoSerie());
                                txtFecha.setText(ordenesCliente.getFechaOrden());
                                txtTelContacto.setText(ordenesCliente.getTelefonoContacto());
                                txtNombreContacto.setText(ordenesCliente.getNombreContacto() + ordenesCliente.getApPContacto());
                                Direcciones direccionEnvio = ordenesCliente.getDireccionEnvio();
                                String dir = direccionEnvio.getEntidadFederativa() + ", " + direccionEnvio.getMunicipio() + ", " +
                                        direccionEnvio.getLocalidad() + ". CP: " + direccionEnvio.getCodigoPostal()
                                        + ". Calle Externa: " + direccionEnvio.getCalleYNumeroExterno() +
                                        ". Calle Interna: " + direccionEnvio.getCalleYNumeroInterno() + ". Con referencias: " + direccionEnvio.getReferencia();
                                txtDireccionContacto.setText(dir);
                                Date dt = new Date();
                                Calendar c = Calendar.getInstance();
                                c.setTime(dt);
                                c.add(Calendar.DATE, 7);
                                DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT);
                                txtFechaDetalle.setText(formateadorFechaCorta.format(c.getTime()));
                                status = ordenesCliente.getEstatusOrden();
                                fechaEntrega = ordenesCliente.getFechaEntrega();
                                fechaEst = ordenesCliente.getFechaOrden();
                                if (status.equals("Pago pendiente")) {
                                    txtFecha.setText("Proximamente");
                                    txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.danger));
                                } else if (status.equals("Preparando pedido")) {
                                    txtFecha.setText("Proximamente");
                                    txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_orange_2));
                                } else if (status.equals("En camino")) {
                                    txtFecha.setText("Proximamente ");
                                    txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_yellow_1));
                                } else if (status.equals("Entregado")) {
                                    txtFecha.setText("Entregado el " + fechaEntrega);
                                    txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_green_1));
                                }

                                txtStatus.setText(status);

                                databaseReference.child("DetalleOrdenesCliente/" + idOrden)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                arrayList.clear();
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {


                                                            DetOrdenProductos ordenesCliente = objSnapshot.getValue(DetOrdenProductos.class);
                                                            arrayList.add(new ElementListViewVerEnvios(
                                                                    ordenesCliente.getModelos(),
                                                                    ordenesCliente.getPrecioUnitario()+"",
                                                                    ordenesCliente.getCantidad()+"",
                                                                    ordenesCliente.getSubtotal(),
                                                                    ordenesCliente.getImgFoto()));
                                                            ListAdapterVerEnviosInterna adapterlista= new ListAdapterVerEnviosInterna(getActivity(), arrayList,getContext());
                                                            listdetalles.setAdapter(adapterlista);
                                                            total = total + (ordenesCliente.getPrecioUnitario() * ordenesCliente.getCantidad());
                                                            txtTotal.setText("$" + total);


                                                    }
                                                }else Toast.makeText(getContext(), "nadaaaa", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        btnConfirmarEntrega.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                VerEnviosFragment newFragment1 = new VerEnviosFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnContactarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactarCliente();
            }
        });

        return root;
    }
    public void inicializarComponentes(){
        txtStatus= root.findViewById(R.id.txtStatusDetalleEnvio);
        txtFecha=root.findViewById(R.id.txtFechaDetalleEnvio);
        txtOrden= root.findViewById(R.id.txtNoOrdenDetalleEnvio);
        txtSerie= root.findViewById(R.id.txtNoSerieDetalleEnvio);
        txtFechaDetalle=root.findViewById(R.id.txtFechaEstimadaDetEnvio);
        txtNombreContacto=root.findViewById(R.id.txtContactoDetEnvio);
        txtTelContacto=root.findViewById(R.id.txtTelefonoDetEnvio);
        txtDireccionContacto= root.findViewById(R.id.txtDireccionDetEnvio);
        txtTotal=root.findViewById(R.id.txtTotalDetalleEnvio);
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
        mViewModel = new ViewModelProvider(this).get(DetalleEnvioViewModel.class);
        // TODO: Use the ViewModel
    }

}