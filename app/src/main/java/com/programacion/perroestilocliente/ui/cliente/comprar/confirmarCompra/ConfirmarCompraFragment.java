package com.programacion.perroestilocliente.ui.cliente.comprar.confirmarCompra;

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
import android.widget.TextView;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosFragment;
import com.programacion.perroestilocliente.ui.cliente.productos.ticket.TicketFragment;

public class ConfirmarCompraFragment extends Fragment {

    private ConfirmarCompraViewModel mViewModel;
    Button btnVerTicket;
    Button btnVerMisPedidos;
    View root;
    String fechaPedido;
    String idOrden;
    float totalApagar;

    TextView fechaEmision;
    TextView fechaLimite;
    TextView total;
    TextView concepto;

    public static ConfirmarCompraFragment newInstance() {
        return new ConfirmarCompraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_confirmar_compra, container, false);

        Bundle arg = getArguments();
        idOrden = arg.getString("noOrden");
        fechaPedido = arg.getString("fechaOrden");
        totalApagar = arg.getFloat("total");

        fechaEmision = root.findViewById(R.id.txtConfCompraFechaEmisionCompra);
        fechaLimite = root.findViewById(R.id.txtConfirmarCompraFechaLimiteCompra);
        total = root.findViewById(R.id.txtConfirmarCompraTotalPagarCompra);
        concepto = root.findViewById(R.id.txtConceptoTransCompra);

        fechaEmision.setText(fechaPedido);
        String []fecha=fechaPedido.split("/");
        String anio=fecha[2];
        String mes=fecha[1];
        String dia=fecha[0];

        int anioF=Integer.parseInt(anio);
        int mesF=Integer.parseInt(mes);
        int diaF=Integer.parseInt(dia);

        String fechaLimit=(dia+3)+"/"+mes+"/"+anio;
        fechaLimite.setText(fechaLimit);
        total.setText("$"+totalApagar+" MX");
        concepto.setText("Perro estilo. No. Pedido: "+idOrden);

        btnVerTicket = root.findViewById(R.id.btnVerTicketCompra);
        btnVerMisPedidos = root.findViewById(R.id.btnVerMisPedidosCompra);

        btnVerTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketFragment newFragment1 = new TicketFragment();
                Bundle args = new Bundle();
                args.putString("noOrden",idOrden);
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
        mViewModel = new ViewModelProvider(this).get(ConfirmarCompraViewModel.class);
        // TODO: Use the ViewModel
    }

}