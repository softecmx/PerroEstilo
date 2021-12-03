package com.programacion.perroestilocliente.ui.administrador.pedidos.verPedidos;

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

import com.google.firebase.FirebaseApp;
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
import com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.VerEnviosFragment;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosFragment;
import com.programacion.perroestilocliente.ui.cliente.productos.ticket.ListAdapterTicket;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VerPedidoDetalleFragment extends Fragment {

    private VerPedidoDetalleViewModel mViewModel;
    Button btnEnviarPedido;
    Button btnImprimirGuia;
    TextView status,fecha,orden,serie,fechaDetalle,nombreContacto,telContacto,direccionContacto;
    TextView total;

    String idOrden;
    String Status = "";
    ListView listView;
    String fechaEst;
    String fechaEntrega;
    float Total;
    String idCliente;
    View root;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseUser user;

    public static VerPedidoDetalleFragment newInstance() {
        return new VerPedidoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_detalle_pedido, container, false);
        Bundle args= getArguments();
        idOrden=args.getString("idOrden");
        idCliente="";
        iniciaFirebase();
        listView=root.findViewById(R.id.lstViewDetalleCompraDetPagos);
        ArrayList <DetOrdenProductos> arrayList= new ArrayList<>();
        llenarDatos();
        btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llamar a la lista de envios
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
        return root;
    }
    public void llenarDatos(){
        inicializarComponentes();
        ArrayList<DetOrdenProductos> arrayListItems = new ArrayList<>();
        Total = 0;
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
                                                        orden.setText(ordenesCliente.getInOrden());
                                                        serie.setText(ordenesCliente.getNoSerie());
                                                        fecha.setText(ordenesCliente.getFechaOrden());
                                                        telContacto.setText(ordenesCliente.getTelefonoContacto());
                                                        nombreContacto.setText(ordenesCliente.getNombreContacto() + ordenesCliente.getApPContacto());
                                                        Direcciones direccionEnvio = ordenesCliente.getDireccionEnvio();
                                                        String dir = direccionEnvio.getEntidadFederativa() + ", " + direccionEnvio.getMunicipio() + ", " +
                                                                direccionEnvio.getLocalidad() + ". CP: " + direccionEnvio.getCodigoPostal()
                                                                + ". Calle Externa: " + direccionEnvio.getCalleYNumeroExterno() +
                                                                ". Calle Interna: " + direccionEnvio.getCalleYNumeroInterno() + ". Con referencias: " + direccionEnvio.getReferencia();
                                                        direccionContacto.setText(dir);
                                                        Date dt = new Date();
                                                        Calendar c = Calendar.getInstance();
                                                        c.setTime(dt);
                                                        c.add(Calendar.DATE, 7);
                                                        DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT);
                                                        fechaDetalle.setText(formateadorFechaCorta.format(c.getTime()));
                                                        Status = ordenesCliente.getEstatusOrden();
                                                        fechaEntrega = ordenesCliente.getFechaEntrega();
                                                        fechaEst = ordenesCliente.getFechaOrden();
                                                        if (status.equals("Pago pendiente")) {
                                                            fecha.setText("Proximamente");
                                                            status.setTextColor(ContextCompat.getColor(getContext(), R.color.danger));
                                                        } else if (status.equals("Preparando pedido")) {
                                                            fecha.setText("Proximamente");
                                                            status.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_orange_2));
                                                        } else if (status.equals("En camino")) {
                                                            fecha.setText("Proximamente ");
                                                            status.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_yellow_1));
                                                        } else if (status.equals("Entregado")) {
                                                            fecha.setText("Entregado el " + fechaEntrega);
                                                            status.setTextColor(ContextCompat.getColor(getContext(), R.color.flat_green_1));
                                                        }

                                                        status.setText(Status);

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
                                                                                    ListAdapterTicket adapterProductos = new ListAdapterTicket(getActivity(), arrayListItems);
                                                                                    listView.setAdapter(adapterProductos);
                                                                                    Total = Total + (ordenesCliente.getPrecioUnitario() * ordenesCliente.getCantidad());
                                                                                    total.setText("$" + total);
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
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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
    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    /*public void botones(){
        if(btnEnviarPedido.callOnClick()){
            btnEnviarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //llamar a la lista de envios
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
        }
        if(btnImprimirGuia.callOnClick()){
            btnImprimirGuia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Pdf del envio
                }
            });
        }

    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerPedidoDetalleViewModel.class);
        // TODO: Use the ViewModel
    }

}