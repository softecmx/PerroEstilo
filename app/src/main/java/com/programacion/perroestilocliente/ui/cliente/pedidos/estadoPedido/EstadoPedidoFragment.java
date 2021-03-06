package com.programacion.perroestilocliente.ui.cliente.pedidos.estadoPedido;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.DetOrdenProductos;
import com.programacion.perroestilocliente.modelo.Direcciones;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EstadoPedidoFragment extends Fragment {

    Button btnVerMisPedidos;
    Button btnVerReferencia;
    View root;
    String idOrden;
    String status = "";
    TextView txtOrden;
    TextView txtSerie;
    TextView txtFechaEstimada;
    TextView txtContacto;
    TextView txtTelefono;
    TextView txtDireccion;
    TextView txtFechaPedido;
    TextView txtTotal;
    TextView txtStatus;
    String fechaEst;
    String fechaEntrega;
    float total;
    String idCliente;
    private EstadoPedidoViewModel mViewModel;


    androidx.recyclerview.widget.RecyclerView reciclerViewPedidos;
    private RVAdapterProdPedido adapterProductos;


    public static EstadoPedidoFragment newInstance() {
        return new EstadoPedidoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_estado_pedido, container, false);
        Bundle args = getArguments();
        idOrden = args.getString("idOrden");
        btnVerMisPedidos = root.findViewById(R.id.btnEstPedMisPedidos);
        btnVerReferencia = root.findViewById(R.id.btnEstPedVerReferencia);
        txtOrden = root.findViewById(R.id.txtEstPedNoOrden);
        txtSerie = root.findViewById(R.id.txtEstPedNoSerie);
        txtFechaEstimada = root.findViewById(R.id.txtEstPedFecha);
        txtContacto = root.findViewById(R.id.txtEstPedContacto);
        txtTelefono = root.findViewById(R.id.txtEstPedTelefono);
        txtDireccion = root.findViewById(R.id.txtEstPedDireccion);
        txtFechaPedido = root.findViewById(R.id.txtEstPedFechaEstimada);
        txtTotal = root.findViewById(R.id.txtEstPedTotal);
        txtStatus = root.findViewById(R.id.txtEstPedEstatus);
        idCliente = "";
        btnVerReferencia = root.findViewById(R.id.btnEstPedVerReferencia);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // ListView reciclerViewMiCarritoProductos = root.findViewById(R.id.lstViewEstPedDet);
        ArrayList<DetOrdenProductos> arrayListItems = new ArrayList<>();
        total = 0;
        reciclerViewPedidos = root.findViewById(R.id.reciclerViewEstPed);

        Query queryCliente = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(user.getEmail());
        queryCliente.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                Clientes usuario = objSnapshot.getValue(Clientes.class);
                                idCliente = usuario.getIdUsuario();
                                databaseReference.child("OrdenesCliente/" + idCliente)
                                        .orderByChild("inOrden").equalTo(idOrden)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                arrayListItems.clear();
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                                        OrdenesCliente ordenesCliente = objSnapshot.getValue(OrdenesCliente.class);
                                                        txtOrden.setText(ordenesCliente.getInOrden());
                                                        txtSerie.setText(ordenesCliente.getNoSerie());
                                                        txtFechaPedido.setText(ordenesCliente.getFechaOrden());
                                                        txtTelefono.setText(ordenesCliente.getTelefonoContacto());
                                                        txtContacto.setText(ordenesCliente.getNombreContacto() + ordenesCliente.getApPContacto());
                                                        Direcciones direccionEnvio = ordenesCliente.getDireccionEnvio();
                                                        String dir = direccionEnvio.getEntidadFederativa() + ", " + direccionEnvio.getMunicipio() + ", " +
                                                                direccionEnvio.getLocalidad() + ". CP: " + direccionEnvio.getCodigoPostal()
                                                                + ". Calle Externa: " + direccionEnvio.getCalleYNumeroExterno() +
                                                                ". Calle Interna: " + direccionEnvio.getCalleYNumeroInterno() + ". Con referencias: " + direccionEnvio.getReferencia();
                                                        txtDireccion.setText(dir);
                                                        Date dt = new Date();
                                                        Calendar c = Calendar.getInstance();
                                                        c.setTime(dt);
                                                        c.add(Calendar.DATE, 7);
                                                        DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT);
                                                        txtFechaEstimada.setText(formateadorFechaCorta.format(c.getTime()));
                                                        status = ordenesCliente.getEstatusOrden();
                                                        fechaEntrega = ordenesCliente.getFechaEntrega();
                                                        fechaEst = ordenesCliente.getFechaOrden();
                                                        if (status.equals("Pago pendiente")) {
                                                            txtFechaEstimada.setText("Proximamente");
                                                            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.danger));
                                                        } else if (status.equals("Preparando pedido")) {
                                                            txtFechaEstimada.setText("Proximamente");
                                                            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_orange_1));
                                                        } else if (status.equals("En camino")) {
                                                            txtFechaEstimada.setText("Proximamente ");
                                                            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_yellow_1));
                                                        } else if (status.equals("Entregado")) {
                                                            txtFechaEstimada.setText("Entregado el " + fechaEntrega);
                                                            txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_green_1));
                                                        }

                                                        txtStatus.setText(status);

                                                        ///
                                                        databaseReference.child("DetalleOrdenesCliente/" + idOrden)
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                        arrayListItems.clear();
                                                                        if (snapshot.exists()) {
                                                                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {

                                                                                try {


                                                                                    DetOrdenProductos ordenesCliente = objSnapshot.getValue(DetOrdenProductos.class);
                                                                                    arrayListItems.add(ordenesCliente);
                                                                                    // ListAdapterTicket adapterProductos = new ListAdapterTicket(getActivity(), arrayListItems);
                                                                                    //  reciclerViewMiCarritoProductos.setAdapter(adapterProductos);
                                                                                    adapterProductos = new RVAdapterProdPedido(root.getContext(), arrayListItems, getFragmentManager());

                                                                                    reciclerViewPedidos.setLayoutManager(new LinearLayoutManager(root.getContext()));
                                                                                    reciclerViewPedidos.setAdapter(adapterProductos);
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
                                                        ///
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
        btnVerReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarReferencia();
            }
        });

        btnVerMisPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MisPedidosFragment newFragment1 = new MisPedidosFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EstadoPedidoViewModel.class);
        // TODO: Use the ViewModel
    }

    public void mostrarReferencia() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirmar_compra, null);
        Button btnCerrar = dialogView.findViewById(R.id.btnDialogCerrar);
        dialogo.setView(dialogView);
        AlertDialog dialog_elimAlertDialog = dialogo.create();


        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_elimAlertDialog.dismiss();
            }
        });
        dialog_elimAlertDialog.show();
    }
}