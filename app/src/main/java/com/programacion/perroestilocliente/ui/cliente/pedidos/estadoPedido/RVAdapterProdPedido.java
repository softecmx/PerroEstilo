package com.programacion.perroestilocliente.ui.cliente.pedidos.estadoPedido;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.DetOrdenProductos;
import com.programacion.perroestilocliente.modelo.Productos;

import java.util.ArrayList;

public class RVAdapterProdPedido extends RecyclerView.Adapter<RVAdapterProdPedido.DataObjectHolder> {
    float redondeo = 0;
    private Context context;
    FragmentManager fragmentManager;
    private ArrayList<DetOrdenProductos> listaProductos;
    private String id;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public RVAdapterProdPedido(Context context, ArrayList<DetOrdenProductos> listaProductos, FragmentManager fragmentManager) {
        this.context = context;
        this.listaProductos = listaProductos;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cardview_prod_pedido, viewGroup, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {

      //  holder.txtNombre.setText(listaProductos.get(holder.getAdapterPosition()).getNombre());

        holder.txtCarritoPrecioUnitario.setText("$" + listaProductos.get(holder.getAdapterPosition()).getPrecioUnitario());
        holder.txtCarritoSubtotal.setText("Subtotal: $"+String.valueOf(listaProductos.get(holder.getAdapterPosition()).getPrecioUnitario()*listaProductos.get(holder.getAdapterPosition()).getCantidad()));
        storageReference.child(listaProductos.get(holder.getAdapterPosition()).getImgFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.imgCarrito);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        holder.txtCarritoCandidad.setText("Cantidad: " + listaProductos.get(holder.getAdapterPosition()).getCantidad());
        databaseReference.child("Productos").orderByChild("idProducto").equalTo(listaProductos.get(holder.getAdapterPosition()).getIdProducto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        Productos item = objSnapshot.getValue(Productos.class);
                        holder.txtNombreProductoCarrito.setText(item.getNombre());
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imgCarrito;
        TextView txtNombreProductoCarrito;
        TextView txtCarritoCandidad;
        TextView txtCarritoSubtotal;
        TextView txtCarritoPrecioUnitario;


        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);

            imgCarrito = itemView.findViewById(R.id.imgPedFoto);
            txtNombreProductoCarrito = itemView.findViewById(R.id.txtPedNombre);
            txtCarritoCandidad = itemView.findViewById(R.id.txtPedCandidad);
            txtCarritoSubtotal = itemView.findViewById(R.id.txtPedSubtotal);
            txtCarritoPrecioUnitario = itemView.findViewById(R.id.txtPedPrecioUnitario);
        }
    }

}
