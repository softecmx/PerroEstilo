package com.programacion.perroestilocliente.ui.cliente.mainCliente;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.modelo.Productos;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterCarrito extends ArrayAdapter<Item> {

    private Activity activity;
    private ArrayList<Item> arrayList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ListAdapterCarrito(Activity activity, ArrayList<Item> arrayList) {
        super(activity, R.layout.item_prod_lista_carrito, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_prod_lista_carrito, parent, false);
        }

        ImageView imgCarrito = convertView.findViewById(R.id.imgCarritoFoto);
        TextView txtNombreProductoCarrito = convertView.findViewById(R.id.txtCarritoNombre);
        //  TextView txtCarritoModelo = convertView.findViewById(R.id.txtCarritoModelo);
        //  TextView txtCarritoTalla = convertView.findViewById(R.id.txtCarritoTalla);
        TextView txtCarritoCandidad = convertView.findViewById(R.id.txtCarritoCandidad);
        TextView txtCarritoSubtotal = convertView.findViewById(R.id.txtCarritoSubtotal);
        TextView txtCarritoPrecioUnitario = convertView.findViewById(R.id.txtCarritoPrecioUnitario);


        txtCarritoPrecioUnitario.setText("$" + arrayList.get(position).getPrecio());
        txtCarritoSubtotal.setText("Subtotal: $"+String.valueOf(arrayList.get(position).getPrecio()*arrayList.get(position).getCantidad()));
        storageReference.child(arrayList.get(position).getImg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(imgCarrito);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        txtCarritoCandidad.setText("Cantidad: " + arrayList.get(position).getCantidad());
        databaseReference.child("Productos").orderByChild("idProducto").equalTo(arrayList.get(position).getProducto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        Productos item = objSnapshot.getValue(Productos.class);
                        txtNombreProductoCarrito.setText(item.getNombre());
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        return convertView;
    }
}