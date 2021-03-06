package com.programacion.perroestilocliente.ui.administrador.pedidos.verDetallePedido;

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
import com.programacion.perroestilocliente.ui.administrador.inventario.ElementListViewInventario;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ConsultarPedidosFragment;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ElementListViewConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ListAdapterConsultarPedidos;
import com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ListAdapterConsultarPedidosinterna;
import com.programacion.perroestilocliente.ui.cliente.pedidos.estadoPedido.EstadoPedidoFragment;
import com.programacion.perroestilocliente.ui.cliente.pedidos.estadoPedido.EstadoPedidoViewModel;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosFragment;
import com.programacion.perroestilocliente.ui.cliente.productos.ticket.ListAdapterTicket;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VerPedidoFragment extends Fragment {


    Button btnConfirmarPago;
    Button btnAtras;
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
    String idordengt, statusgt, idusuario;
    float totalgt;
    ListView listdetalles;
    private VerPedidoViewModel mViewModel;

    public static VerPedidoFragment newInstance() {
        return new VerPedidoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_consultar_pedidos_admin, container, false);
        //OBTENER LOS DATOS DEL BUNDLE
        Bundle args = getArguments();
        idOrden = args.getString("idOrden");
        idusuario = args.getString("idusuario");
        statusgt = args.getString("status");
        totalgt = args.getFloat("total");

        listdetalles = root.findViewById(R.id.lstViewEstPedDetAdmin);
        btnConfirmarPago = root.findViewById(R.id.btnCambiarStatusPedido);
        btnAtras = root.findViewById(R.id.btnAtrasPedidoPendienteAdmin);
        txtOrden = root.findViewById(R.id.txtEstPedNoOrdenAdmin);
        txtSerie = root.findViewById(R.id.txtEstPedNoSerieAdmin);
        txtFechaEstimada = root.findViewById(R.id.txtEstPedFechaAdmin);
        txtContacto = root.findViewById(R.id.txtEstPedContactoAdmin);
        txtTelefono = root.findViewById(R.id.txtEstPedTelefonoAdmin);
        txtDireccion = root.findViewById(R.id.txtEstPedDireccionAdmin);
        txtFechaPedido = root.findViewById(R.id.txtEstPedFechaEstimadaAdmin);
        txtTotal = root.findViewById(R.id.txtEstPedTotalAdmin);
        txtStatus = root.findViewById(R.id.txtEstPedEstatusAdmin);
        idCliente = "";
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         listdetalles = root.findViewById(R.id.lstViewEstPedDetAdmin);
        ArrayList<ElementListViewConsultarPedidos> arrayList = new ArrayList<>();
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
                                    txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_orange_2));
                                } else if (status.equals("En camino")) {
                                    txtFechaEstimada.setText("Proximamente ");
                                    txtStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_yellow_1));
                                } else if (status.equals("Entregado")) {
                                    txtFechaEstimada.setText("Entregado el " + fechaEntrega);
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
                                                                    ordenesCliente.getPrecioUnitario()+"",
                                                                    ordenesCliente.getCantidad()+"",
                                                                    ordenesCliente.getSubtotal(),
                                                                    ordenesCliente.getImgFoto()));
                                                            ListAdapterConsultarPedidosinterna adapterlista= new ListAdapterConsultarPedidosinterna(getActivity(), arrayList,getContext());
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


        btnConfirmarPago.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        ConsultarPedidosFragment newFragment1 = new ConsultarPedidosFragment();
        Bundle args = new Bundle();
        newFragment1.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newFragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    });
        return root;
}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerPedidoViewModel.class);
        // TODO: Use the ViewModel
    }

}