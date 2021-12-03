package com.programacion.perroestilocliente.ui.administrador.pagos.modificarPagosPendientes;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

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
import com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes.PagosPendientesFragment;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ConsultarPedidosFragment;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ListAdapterConsultarPedidosinterna;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModificarPagosPendientesFragment extends Fragment {
    Button btnConfirmarPago;
    Button btnAtras;
    TextView txtStatus, txtFecha, txtOrden, txtSerie, txtFechaDetalle, txtNombreContacto, txtTelContacto, txtDireccionContacto;
    TextView txtTotal;

    String idOrden;
    String status = "";
    String fechaEst;
    String fechaEntrega;
    float total;
    String idCliente;
    String idordengt, statusgt, idusuario;
    float totalgt;
    ListView listdetalles;
    View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ModificarPagosPendientesViewModel mViewModel;

    public static ModificarPagosPendientesFragment newInstance() {
        return new ModificarPagosPendientesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detalle_pagos, container, false);
        btnConfirmarPago = root.findViewById(R.id.btnConfirmarDetallePago);
        btnAtras = root.findViewById(R.id.btnAtrasDetaPago);

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
        listdetalles = root.findViewById(R.id.lstViewDetalleCompraDetPagos);
        ArrayList<ElementListViewConsultarPedidos> arrayList = new ArrayList<>();
        total = 0;
        iniciaFirebase();
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

                                                        try {
                                                            DetOrdenProductos ordenesCliente = objSnapshot.getValue(DetOrdenProductos.class);
                                                            arrayList.add(new ElementListViewConsultarPedidos(
                                                                    ordenesCliente.getModelos(),
                                                                    ordenesCliente.getPrecioUnitario() + "",
                                                                    ordenesCliente.getCantidad() + "",
                                                                    ordenesCliente.getSubtotal(),
                                                                    ordenesCliente.getImgFoto()));
                                                            ListAdapterConsultarPedidosinterna adapterlista = new ListAdapterConsultarPedidosinterna(getActivity(), arrayList, getContext());
                                                            listdetalles.setAdapter(adapterlista);
                                                            total = total + (ordenesCliente.getPrecioUnitario() * ordenesCliente.getCantidad());
                                                            txtTotal.setText("$" + total);
                                                        } catch (Exception e) {

                                                        }

                                                    }
                                                }
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


       /* btnConfirmarPago.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                PagosPendientesFragment newFragment1 = new PagosPendientesFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagosPendientesFragment newFragment1 = new PagosPendientesFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/
     modificarStatus();
        return root;
    }

    public void inicializarComponentes() {
        txtStatus = root.findViewById(R.id.txtStatusDetPagos);
        txtFecha = root.findViewById(R.id.txtFechaDetPagos);
        txtOrden = root.findViewById(R.id.txtNoOrdenDetPagos);
        txtSerie = root.findViewById(R.id.txtNoSerieDetPagos);
        txtFechaDetalle = root.findViewById(R.id.txtFechaEstimadaDetPago);
        txtNombreContacto = root.findViewById(R.id.txtContactoDetPago);
        txtTelContacto = root.findViewById(R.id.txtTelefonoDetPago);
        txtDireccionContacto = root.findViewById(R.id.txtDireccionDetPago);
        txtTotal = root.findViewById(R.id.txtTotalDetPago);
    }

    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ModificarPagosPendientesViewModel.class);
        // TODO: Use the ViewModel
    }

    public void modificarStatus() {
        iniciaFirebase();
        Map<String, Object> encenderMap = new HashMap<>();
        encenderMap.put("OrdenesCliente/" + idusuario+"/"+idOrden+"/estatusOrden", "Preparando pedido");
        //encenderMap.put("Productos/"+txtCodigo.getText()+"/estatusStock",spnModStatus.getText().toString());

        databaseReference.updateChildren(encenderMap);
        if (status == "Pago pendiente") {
            btnConfirmarPago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConsultarPedidosFragment newFragment1 = new ConsultarPedidosFragment();
                    Bundle args = new Bundle();
                    newFragment1.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, newFragment1);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    status = "Preparando pedido";
                    Toast.makeText(getContext(), "Status actualizado", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagosPendientesFragment newFragment1 = new PagosPendientesFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //Toast.makeText(getContext(), "Inventario Actualizado", Toast.LENGTH_SHORT).show();
        //dialog.dismiss();
    }

}