package com.programacion.perroestilocliente.ui.administrador.productos.buscarProductos;

import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;

public class BuscarProductosFragment extends Fragment {

    TextView tvCategoria,tvDiseño,tvTalla,tvCosto,tvPrecio,tvDescuento,tvStock,tvEstatus,tvModelo,tvDescripción,tvNombre,tvCodigo;
    ImageButton busca;
    ImageView ivImagen;
    EditText etId;

    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;

    View root;

    private BuscarProductosViewModel mViewModel;

    public static BuscarProductosFragment newInstance() {
        return new BuscarProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_buscar_productos_administrador, container, false);
        carga();
        iniciaFirebase();
        return root;
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    public void carga(){
        tvCategoria = root.findViewById(R.id.etBuscProdCategoria);
        tvDiseño = root.findViewById(R.id.etBuscProdDisenio);
        tvTalla = root.findViewById(R.id.etBuscProdTalla);
        tvCosto = root.findViewById(R.id.etBuscProdCosto);
        tvPrecio = root.findViewById(R.id.etBuscProdPrecio);
        tvDescuento = root.findViewById(R.id.etBuscProdDescuento);
        tvStock = root.findViewById(R.id.etBuscProdStock);
        tvEstatus = root.findViewById(R.id.etBuscProdEstatus);
        tvModelo = root.findViewById(R.id.etBuscProdCodigo);
        tvDescripción = root.findViewById(R.id.etBuscProdDescripcion);
        tvNombre = root.findViewById(R.id.etBuscProdNombre);
        busca = root.findViewById(R.id.ibtnBuscaProdbsc);
        ivImagen = root.findViewById(R.id.imgBPfoto);
        etId = root.findViewById(R.id.etBPid);


        star1=root.findViewById(R.id.ivBuscaProdStar1);
        star2=root.findViewById(R.id.ivBuscaProdStar2);
        star3=root.findViewById(R.id.ivBuscaProdStar3);
        star4=root.findViewById(R.id.ivBuscaProdStar4);
        star5=root.findViewById(R.id.ivBuscaProdStar5);

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
    }
    public void buscar() {
        String id = etId.getText().toString();
        if (id.equals("")) {
            CustomToast.mostarToast("Elija un dato", 2, false, root, getLayoutInflater(), getContext());
        } else {
            databaseReference.child("Productos").orderByChild("idProducto").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Productos c = null;
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        c = objSnapshot.getValue(Productos.class);
                    }
                    if (c != null) {
                        tvNombre.setText(c.getNombre());
                        tvCosto.setText(c.getCosto());
                        tvPrecio.setText(c.getPrecioVenta());
                        tvDescuento.setText(c.getDescuento());
                        tvStock.setText(c.getStock());
                        tvEstatus.setText(c.getEstatusStock());
                        tvModelo.setText(c.getIdProducto());
                        tvDescripción.setText(c.getDescripcion());
                        cargaImagen(ivImagen, c.getImgFoto());
                        etId.setEnabled(false);

                        databaseReference.child("Disenios").orderByChild("idModelo").equalTo(c.getDisenio()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Disenios c = null;
                                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                    c = objSnapshot.getValue(Disenios.class);
                                }
                                if (c != null) {
                                    tvDiseño.setText(c.getDisenio());
                                } else {

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        databaseReference.child("Tallas").orderByChild("idTalla").equalTo(c.getTalla()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Tallas c = null;
                                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                    c = objSnapshot.getValue(Tallas.class);
                                }
                                if (c != null) {
                                    tvTalla.setText(c.getTallas());
                                } else {

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        databaseReference.child("Categorias").orderByChild("idCategorias").equalTo(c.getIdCategoria()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Categorias c = null;
                                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                    c = objSnapshot.getValue(Categorias.class);
                                }
                                if (c != null) {
                                    tvCategoria.setText(c.getNombreCategoria());
                                } else {

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        float estrella = Float.parseFloat(c.getRaiting());
                        estrella = (float) Math.round(estrella);
                        String est = "" + estrella;
                        System.out.println(est + " promedio");
                        switch (est) {
                            case "0":
                                break;
                            case "1.0":
                                star1.setImageResource(R.drawable.ic_star_black_24dp);
                                break;
                            case "2.0":
                                star1.setImageResource(R.drawable.ic_star_black_24dp);
                                star2.setImageResource(R.drawable.ic_star_black_24dp);
                                break;
                            case "3.0":
                                star1.setImageResource(R.drawable.ic_star_black_24dp);
                                star2.setImageResource(R.drawable.ic_star_black_24dp);
                                star3.setImageResource(R.drawable.ic_star_black_24dp);
                                break;
                            case "4.0":
                                star1.setImageResource(R.drawable.ic_star_black_24dp);
                                star2.setImageResource(R.drawable.ic_star_black_24dp);
                                star3.setImageResource(R.drawable.ic_star_black_24dp);
                                star4.setImageResource(R.drawable.ic_star_black_24dp);
                                break;
                            case "5.0":
                                star1.setImageResource(R.drawable.ic_star_black_24dp);
                                star2.setImageResource(R.drawable.ic_star_black_24dp);
                                star3.setImageResource(R.drawable.ic_star_black_24dp);
                                star4.setImageResource(R.drawable.ic_star_black_24dp);
                                star5.setImageResource(R.drawable.ic_star_black_24dp);
                                break;
                        }
                    } else {
                        CustomToast.mostarToast("Dato no encontrado", 3, false, root, getLayoutInflater(), getContext());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    private void cargaImagen(ImageView ivFoto, String img) {
        storageReference.child(img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(ivFoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Ups! Ha ocurrido un erro al recuperar la imagen\n" + e.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BuscarProductosViewModel.class);
        // TODO: Use the ViewModel
    }

}