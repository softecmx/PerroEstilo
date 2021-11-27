package com.programacion.perroestilocliente.ui.administrador.disenios;

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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

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
import com.google.firebase.storage.UploadTask;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Disenios;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ListAdapter extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;
    LayoutInflater layoutInflater;
    Context context;
    View root;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String img="";

    private Button btnPopCerrar;
    private Button btnPopAgregar;
    private EditText etPopNombre;
    private ImageView ivPopImg;
    private AutoCompleteTextView listaPop;
    com.google.android.material.floatingactionbutton.FloatingActionButton fbPopFoto;
    private androidx.appcompat.app.AlertDialog dialog;
    String id="";
    Disenios c;


    public ListAdapter(Activity activity, ArrayList<ElementListView> arrayList,LayoutInflater layoutInflater,Context context,View root) {
           super(activity, R.layout.item_lista_tallas, arrayList);
           this.activity = activity;
           this.arrayList = arrayList;
           this.layoutInflater = layoutInflater;
           this.context = context;
           this.root = root;
           iniciaFirebase();
    }

    public void iniciaFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Disenios");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_disenio, parent, false);
        }

        TextView txtCampo= convertView.findViewById(R.id.ListDisenionombre);
        ImageView imgFoto = convertView.findViewById(R.id.ivListDisenioimg);
        Button lvButtonEdita = convertView.findViewById(R.id.btnListDisenioedita);
        Button lvButtonElimina = convertView.findViewById(R.id.btnListDisenioelimina);

        txtCampo.setText(arrayList.get(position).getDisenio());
        cargaImagen(imgFoto, arrayList.get(position).getImagen());

        lvButtonElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elimina(position);
            }
        });

        lvButtonEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edita(position);
            }
        });

        return convertView;
    }
    public void edita(int position){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_disenio, null);

        btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnAgrDisenioCancelar);
        btnPopAgregar = (Button) aboutPop.findViewById(R.id.btnAgrDisenioAceptar);
        etPopNombre = (EditText) aboutPop.findViewById(R.id.etADisenioNom);
        ivPopImg =(ImageView) aboutPop.findViewById(R.id.imgAddDisenioFoto);
        listaPop =(AutoCompleteTextView) aboutPop.findViewById(R.id.spAdisenioEt);
        fbPopFoto =  aboutPop.findViewById(R.id.fBtnAddDisenioFoto);
        img = arrayList.get(position).getImagen();

        etPopNombre.setText(arrayList.get(position).getDisenio());
        cargaImagen(ivPopImg,img);


        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        id = arrayList.get(position).getIdModelo();
        fbPopFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnPopCerrar.setOnClickListener(view -> dialog.dismiss());
        btnPopAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.equals("") || etPopNombre.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Faltan datos", Toast.LENGTH_SHORT).show();

                }else{
                    Disenios disenio = new Disenios();
                    disenio.setDisenio(etPopNombre.getText().toString());
                    disenio.setImagen(img);
                    disenio.setEstadoLogico("1");
                    disenio.setIdModelo(id);
                    databaseReference.child("Disenios").child(id).setValue(disenio);
                    Toast.makeText(getContext(), "Datos modificados!", Toast.LENGTH_SHORT).show();

                    etPopNombre.setText("");
                    listaPop.setText("");
                    ivPopImg.setImageResource(R.drawable.no_image);
                    img = "";
                    dialog.dismiss();
                }
            }
        });
    }
    public void elimina(int position){
        databaseReference.child("Disenios").orderByChild("idModelo").equalTo(arrayList.get(position).getIdModelo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c=null;
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    c = objSnapshot.getValue(Disenios.class);
                }
                if (c!=null){
                    c.setEstadoLogico("0");
                    databaseReference.child("Disenios").child(c.getIdModelo()).setValue(c);
                    Toast.makeText(context,"Dato eliminado",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Dato NO encontrado",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void cargaImagen(ImageView ivFoto, String img) {
        storageReference.child(img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(ivFoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Ups! Ha ocurrido un erro al recuperar la imagen\n" + e.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}