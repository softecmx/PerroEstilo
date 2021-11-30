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

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosFragment;
import com.programacion.perroestilocliente.ui.cliente.productos.ticket.TicketFragment;

public class ConfirmarCompraFragment extends Fragment {

    private ConfirmarCompraViewModel mViewModel;
    Button btnVerTicket;
    Button btnVerMisPedidos;
    View root;
    public static ConfirmarCompraFragment newInstance() {
        return new ConfirmarCompraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_confirmar_compra, container, false);
        btnVerTicket=root.findViewById(R.id.btnVerTicketCompra);
        btnVerMisPedidos=root.findViewById(R.id.btnVerMisPedidosCompra);

        btnVerTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketFragment newFragment1 = new TicketFragment();
                Bundle args = new Bundle();
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