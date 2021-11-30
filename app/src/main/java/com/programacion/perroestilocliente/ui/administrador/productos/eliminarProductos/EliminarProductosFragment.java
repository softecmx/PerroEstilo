package com.programacion.perroestilocliente.ui.administrador.productos.eliminarProductos;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import com.google.firebase.storage.UploadTask;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EliminarProductosFragment extends Fragment {

    private EliminarProductosViewModel mViewModel;
    View root;

    TextView tvCategoria,tvDiseño,tvTalla,tvCosto,tvPrecio,tvDescuento,tvStock,tvEstatus,tvDescripción,tvCodigo,tvNombre;
    Button btnEliminar,btnLimpia;
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

    public static EliminarProductosFragment newInstance() {
        return new EliminarProductosFragment();
    }
    int enc=1;
    String id="";

    Productos c = new Productos();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_eliminar_productos, container, false);
        carga();
        iniciaFirebase();
        return root;
    }
    public void carga(){
        etId=root.findViewById(R.id.etElimProdid);
        tvCategoria=root.findViewById(R.id.etElimProdCategoria);
        tvDiseño=root.findViewById(R.id.etElimProdDisenio);
        tvTalla=root.findViewById(R.id.etElimProdTalla);
        tvCosto=root.findViewById(R.id.etElimProdCosto);
        tvPrecio=root.findViewById(R.id.etElimProdPrecio);
        tvDescuento=root.findViewById(R.id.etElimProdDescuento);
        tvStock=root.findViewById(R.id.etElimProdStock);
        tvEstatus=root.findViewById(R.id.etElimProdEstatus);
        tvCodigo=root.findViewById(R.id.etElimProdCodigo);
        tvDescripción=root.findViewById(R.id.etElimProdDescripcion);
        btnEliminar=root.findViewById(R.id.btnElimProductelimina);
        btnLimpia=root.findViewById(R.id.btnElimProductLimpia);
        busca=root.findViewById(R.id.ibtnElimProdbsc);
        ivImagen = root.findViewById(R.id.imgElimProdfoto);
        tvNombre = root.findViewById(R.id.etElimProdNombre);

        star1=root.findViewById(R.id.ivElimProdStar1);
        star2=root.findViewById(R.id.ivElimProdStar2);
        star3=root.findViewById(R.id.ivElimProdStar3);
        star4=root.findViewById(R.id.ivElimProdStar4);
        star5=root.findViewById(R.id.ivElimProdStar5);

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
        btnLimpia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
    }
    public void eliminar(){
        if (enc==1){
            databaseReference.child("Productos").child(c.getIdProducto()).removeValue();
            CustomToast.mostarToast("Dato eliminado!",1,false,root,getLayoutInflater(),getContext());
            limpiar();
        }else{
            CustomToast.mostarToast("Seleccione un registro",2,false,root,getLayoutInflater(),getContext());
        }
    }
    public void limpiar(){
        tvNombre.setText("");
        tvCategoria.setText("");
        tvDiseño.setText("");
        tvTalla.setText("");
        tvCosto.setText("");
        tvPrecio.setText("");
        tvDescuento.setText("");
        tvStock.setText("");
        tvEstatus.setText("");
        tvCodigo.setText("");
        tvDescripción.setText("");
        etId.setText("");
        ivImagen.setImageResource(R.drawable.no_image);
        etId.setEnabled(true);
        enc=0;
    }
    public void buscar(){
        id = etId.getText().toString();
        if (id.equals("")){
            CustomToast.mostarToast("Elija un dato",2,false,root,getLayoutInflater(),getContext());
        }else{
            databaseReference.child("Productos").orderByChild("idProducto").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot){
                    c=null;
                    for (DataSnapshot objSnapshot: snapshot.getChildren()){
                        c = objSnapshot.getValue(Productos.class);
                    }
                    if (c!=null){
                        enc=1;
                        tvNombre.setText(c.getNombre());
                        tvTalla.setText(c.getTalla());
                        tvCosto.setText(c.getCosto());
                        tvPrecio.setText(c.getPrecioVenta());
                        tvDescuento.setText(c.getDescuento());
                        tvStock.setText(c.getStock());
                        tvEstatus.setText(c.getEstatusStock());
                        tvCodigo.setText(c.getIdProducto());
                        tvDescripción.setText(c.getDescripcion());
                        cargaImagen(ivImagen,c.getImgFoto());
                        etId.setEnabled(false);

                        databaseReference.child("Disenios").orderByChild("idModelo").equalTo(c.getDisenio()).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot){
                                Disenios c=null;
                                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                                    c = objSnapshot.getValue(Disenios.class);
                                }
                                if (c!=null){
                                    tvDiseño.setText(c.getDisenio());
                                }else{

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error){

                            }
                        });
                        databaseReference.child("Tallas").orderByChild("idTalla").equalTo(c.getTalla()).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot){
                                Tallas c=null;
                                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                                    c = objSnapshot.getValue(Tallas.class);
                                }
                                if (c!=null){
                                    tvTalla.setText(c.getTallas());
                                }else{

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error){

                            }
                        });
                        databaseReference.child("Categorias").orderByChild("idCategorias").equalTo(c.getIdCategoria()).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot){
                                Categorias c=null;
                                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                                    c = objSnapshot.getValue(Categorias.class);
                                }
                                if (c!=null){
                                    tvCategoria.setText(c.getNombreCategoria());
                                }else{

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error){

                            }
                        });
                        float estrella = Float.parseFloat(c.getRaiting());
                        estrella = (float) Math.round(estrella);
                        String est=""+estrella;
                        System.out.println(est+" promedio");
                        switch (est){
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
                    }else{
                        enc=0;
                        CustomToast.mostarToast("Dato no encontrado 1",3,false,root,getLayoutInflater(),getContext());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error){
                }
            });
        }
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EliminarProductosViewModel.class);
        // TODO: Use the ViewModel
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
}