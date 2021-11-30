package com.programacion.perroestilocliente.ui.cliente.mainCliente;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda.VerProductoTiendaFragment;

import java.util.ArrayList;

public class ReciclerViewAdapterProductosCarro extends RecyclerView.Adapter<ReciclerViewAdapterProductosCarro.DataObjectHolder> {

    private Context context;
    private ArrayList<Item> listaProductos;
    private String id;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");

    public ReciclerViewAdapterProductosCarro(Context context, ArrayList<Item> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_lista_carrito, viewGroup, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {

       // holder.txtNombre.setText(listaProductos.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtNombre;
        private TextView txtDescuento;
        private TextView txtOferta;
        private TextView txtPrecio;
        private RatingBar ratingBar;
        private Button btnVer;
        private Button btnAgregarCarrito;


        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
         //   this.img = itemView.findViewById(R.id.imgCardProductoTienda);
        }
    }

}
