package com.programacion.perroestilocliente.ui.cliente.carrito.miCarrito;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class ListAdapterCarrito extends ArrayAdapter<Item>{
    LayoutInflater layoutInflater;
    Context context;
    View root;
    private Activity activity;
    private ArrayList<Item> arrayList;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ImageView imgCarrito;
    TextView txtNombreProductoCarrito;
    //  TextView txtCarritoModelo = convertView.findViewById(R.id.txtCarritoModelo);
    //  TextView txtCarritoTalla = convertView.findViewById(R.id.txtCarritoTalla);
    Button btnVer;
    Button btnEdit;
    Button btnQuitar;
    private androidx.appcompat.app.AlertDialog dialog;
    TextView txtCarritoCandidad;


    TextView txtCarritoSubtotal;
    TextView txtCarritoPrecioUnitario;

    public ListAdapterCarrito(Activity activity, ArrayList<Item> arrayList, LayoutInflater layoutInflater, Context context, View root) {
        super(activity, R.layout.item_prod_lista_carrito_editar, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        this.layoutInflater = layoutInflater;
        this.context = context;
        this.root = root;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_prod_lista_carrito_editar, parent, false);
        }

         imgCarrito = convertView.findViewById(R.id.imgCarritoEditFoto);
         txtNombreProductoCarrito = convertView.findViewById(R.id.txtCarritoEditNombre);
        //  TextView txtCarritoModelo = convertView.findViewById(R.id.txtCarritoModelo);
        //  TextView txtCarritoTalla = convertView.findViewById(R.id.txtCarritoTalla);
         btnVer=convertView.findViewById(R.id.btnMiCarrEditVer);
         btnEdit=convertView.findViewById(R.id.btnMiCarrEditEditar);
         btnQuitar=convertView.findViewById(R.id.btnMiCarrEditQuit);

         txtCarritoCandidad = convertView.findViewById(R.id.editMiCarrEditCantidad);


         txtCarritoSubtotal = convertView.findViewById(R.id.txtCarritoEditSubtotal);
         txtCarritoPrecioUnitario = convertView.findViewById(R.id.txtCarritoPrecioEditUnitario);

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

        txtCarritoCandidad.setText(String.valueOf(arrayList.get(position).getCantidad()));
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
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edita(position);
            }
        });
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             verProducto(position);
            }
        });
        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitar(position);
            }
        });

        return convertView;
    }
    public  void edita(int position){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_editar_carrito, null);

      //  etPopNombre.setText(arrayList.get(position).getDisenio());

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
    }
    public void quitar(int position){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_quitar_carrito, null);



        //  etPopNombre.setText(arrayList.get(position).getDisenio());

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
    }
    public void verProducto(int position){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_ver_prod_carrito, null);

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
    }


}