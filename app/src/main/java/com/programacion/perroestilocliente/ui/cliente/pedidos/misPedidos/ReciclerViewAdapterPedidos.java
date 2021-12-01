package com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.pedidos.estadoPedido.EstadoPedidoFragment;
import com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda.VerProductoTiendaFragment;

import java.util.ArrayList;

public class ReciclerViewAdapterPedidos extends RecyclerView.Adapter<ReciclerViewAdapterPedidos.DataObjectHolder> {
    float redondeo = 0;
    private Context context;
    FragmentManager fragmentManager;
    private ArrayList<OrdenesCliente> listaPedidos;
    private String id;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");

    public ReciclerViewAdapterPedidos(Context context, ArrayList<OrdenesCliente> listaPedidos, FragmentManager fragmentManager) {
        this.context = context;
        this.listaPedidos = listaPedidos;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_item_pedido, viewGroup, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {


        holder.txtNoOrden.setText(listaPedidos.get(holder.getPosition()).getInOrden());
        //  holder.txtStatus
        holder.txtTotal.setText("$"+listaPedidos.get(holder.getPosition()).getTotal()+" MX");
        holder.txtFechaOrden.setText(listaPedidos.get(holder.getPosition()).getFechaOrden());
        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EstadoPedidoFragment newFragment1 = new EstadoPedidoFragment();
                Bundle args = new Bundle();
                args.putString("idOrden",listaPedidos.get(holder.getPosition()).getInOrden());
                newFragment1.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        switch (listaPedidos.get(holder.getPosition()).getEstatusOrden().toString()) {
            case "Pago pendiente":
                holder.txtStatus.setText("Pago pendiente");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.danger));

                break;
            case "Preparando pedido":
                holder.txtStatus.setText("Preparando");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.flat_orange_2));
                break;
            case "En camino":
                holder.txtStatus.setText("En camino");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.flat_yellow_1));
                break;
            case "Entregado":
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.flat_green_1));
                holder.txtStatus.setText("Entregado");
                break;
            default:
                holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.txtStatus.setText("Pago Pendiente");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtNoOrden;
        private Button btnVer;
        private TextView txtFechaOrden;
        private TextView txtTotal;
        private TextView txtStatus;


        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.imgItemPedido);
            this.txtFechaOrden = itemView.findViewById(R.id.txtItemPedidoFecha);
            this.btnVer = itemView.findViewById(R.id.btnPedidoVerCompra);
            this.txtTotal = itemView.findViewById(R.id.txtItemPedidoTotal);
            this.txtNoOrden = itemView.findViewById(R.id.txtItemPedidoNoSerie);
            this.txtStatus = itemView.findViewById(R.id.txtItemPedidoStatus);
        }
    }

}
