package com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Productos;

public class VerProductoTiendaFragment extends Fragment {

    private VerProductoTiendaViewModel mViewModel;
    View root;
    TextView txtNombre;
    TextView txtPrecio;
    TextView txtdescuento;
    TextView txtPrecioAntiguo;
    TextView txtdesc;
    RatingBar ratingBar;
    ImageView img;
    Button btnAgregar;
    Button btnAyuda;
    Button btnComprarAhora;
    Button btnmas;
    Button btnmenos;
    AutoCompleteTextView talla;
    EditText cantidad;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public static VerProductoTiendaFragment newInstance() {
        return new VerProductoTiendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ver_producto_tienda, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        Bundle arg = getArguments();
        String id = arg.getString("idProducto");
        System.out.println(id+" VER PRODUCTOS");
        txtNombre = root.findViewById(R.id.txtVerProdNombre);
        txtPrecio = root.findViewById(R.id.txtVerProdPrecio);
        ratingBar = root.findViewById(R.id.raitingBarVerProd);
        img = root.findViewById(R.id.imgVerProd);
        btnAgregar = root.findViewById(R.id.btnVerProdAgregarCar);
        btnAyuda = root.findViewById(R.id.btnVerProdPreguntar);
        btnComprarAhora = root.findViewById(R.id.btnVerProdComprarAhora);
        btnmenos = root.findViewById(R.id.btnVerProdMenos);
        btnmas = root.findViewById(R.id.btnVerProdMas);
        txtdescuento = root.findViewById(R.id.txtVerProdDescuento);
        cantidad = root.findViewById(R.id.editVerProdCantidad);
        talla = root.findViewById(R.id.spinVerProdTalla);
        txtdesc = root.findViewById(R.id.txtVerProdDesc);
        txtPrecioAntiguo=root.findViewById(R.id.txtVerProdPrecioAntiguo);
        Query queryMascota = databaseReference.child("Productos").orderByChild("idProducto").equalTo(id);
        queryMascota.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        Productos productos = objSnapshot.getValue(Productos.class);
                        txtNombre.setText(productos.getNombre());
                        txtPrecio.setText(productos.getPrecioVenta());
                        ratingBar.setRating(Float.parseFloat(productos.getRaiting()));
                        storageReference.child(productos.getImgFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(root).load(uri).into(img);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
                            }
                        });
                        txtdescuento.setText(productos.getDescuento());
                        txtdesc.setText(productos.getDescripcion());
                        String raiting = productos.getRaiting();
                        float raitingStar = 0;
                        if (raiting.isEmpty()) {
                            raitingStar = 0;
                        } else {
                            raitingStar = Float.parseFloat(raiting);
                        }
                       ratingBar.setRating(raitingStar);

                        if (productos.getDescuento().equals("") || productos.getDescuento().equals("0")) {
                            txtdescuento.setVisibility(View.GONE);
                            txtPrecioAntiguo.setVisibility(View.GONE);
                            txtPrecio.setText("$" + productos.getPrecioVenta());
                        } else {
                            txtdescuento.setVisibility(View.VISIBLE);
                            float descuento = Float.parseFloat(productos.getDescuento());

                            float precio = Float.parseFloat(productos.getPrecioVenta());
                            float descuentoReal = (descuento * precio) / 100;
                            float precioActual = precio - descuentoReal;
                            float redondeo = Math.round(precioActual);
                            txtPrecioAntiguo.setText(productos.getPrecioVenta());

                            txtPrecioAntiguo.setPaintFlags(txtPrecioAntiguo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            txtPrecioAntiguo.setText("$" + productos.getPrecioVenta());
                            txtPrecio.setText("$" + redondeo);
                            txtdescuento.setText("Descuento del: "+productos.getDescuento()+"%");
                        }




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerProductoTiendaViewModel.class);
        // TODO: Use the ViewModel
    }

}