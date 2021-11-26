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
import com.programacion.perroestilocliente.modelo.Productos;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EliminarProductosFragment extends Fragment {

    private EliminarProductosViewModel mViewModel;
    View root;

    TextView tvRaiting,tvCategoria,tvDiseño,tvTalla,tvCosto,tvPrecio,tvDescuento,tvStock,tvEstatus,tvModelo,tvDescripción,tvCodigo;
    Button btnEliminar,btnLimpia;
    com.google.android.material.floatingactionbutton.FloatingActionButton fbCamara;
    ImageButton busca;
    ImageView ivImagen;
    EditText etId;

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    public static final int REQUEST_PERMISSION_CAMERA = 100;

    private Uri photoURI;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;

    public static EliminarProductosFragment newInstance() {
        return new EliminarProductosFragment();
    }
    String img="";

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
        tvRaiting=root.findViewById(R.id.etElimProdRaiting);
        tvCategoria=root.findViewById(R.id.etElimProdCategoria);
        tvDiseño=root.findViewById(R.id.etElimProdDisenio);
        tvTalla=root.findViewById(R.id.etElimProdTalla);
        tvCosto=root.findViewById(R.id.etElimProdCosto);
        tvPrecio=root.findViewById(R.id.etElimProdPrecio);
        tvDescuento=root.findViewById(R.id.etElimProdDescuento);
        tvStock=root.findViewById(R.id.etElimProdStock);
        tvEstatus=root.findViewById(R.id.etElimProdEstatus);
        tvCodigo=root.findViewById(R.id.etElimProdCodigo);
        tvModelo=root.findViewById(R.id.etElimProdModelo);
        tvDescripción=root.findViewById(R.id.etElimProdDescripcion);
        btnEliminar=root.findViewById(R.id.btnElimProductelimina);
        btnLimpia=root.findViewById(R.id.btnElimProductLimpia);
        fbCamara=root.findViewById(R.id.fBtnElimProdfoto);
        busca=root.findViewById(R.id.ibtnElimProdbsc);
        ivImagen = root.findViewById(R.id.imgElimProdfoto);
        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
        fbCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    }
    public void limpiar(){

    }
    public void buscar(){
        String id = etId.getText().toString();
        if (id.equals("")){
            CustomToast.mostarToast("Elija un dato",2,false,root,getLayoutInflater(),getContext());
        }else{
            databaseReference.child("Productos").orderByChild("iIdProducto").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot){
                    Productos c=null;
                    for (DataSnapshot objSnapshot: snapshot.getChildren()){
                        c = objSnapshot.getValue(Productos.class);
                    }
                    if (c!=null){
                       /* tvNombre.setText(c.getNombreCategoria());
                        tvDesc.setText(c.getDescripcion());
                        tvPublico.setText(c.getTipoPublico());
                        CustomToast.mostarToast("Dato encontrado!",1,true,root,getLayoutInflater(),getContext());
                        bnd=1;
                        search.setEnabled(false);
                        idACTV.setEnabled(false);
                        idACTV.setAdapter(null);
                    }else{
                        CustomToast.mostarToast("Dato NO encontrado!",3,false,root,getLayoutInflater(),getContext());
                        bnd=0;*/
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
    private void abrirCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, COD_FOTO);
            }
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COD_SELECCIONA:
                if(data!=null){
                    Uri miPath = data.getData();
                    ivImagen.setImageURI(miPath);
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContext().getContentResolver().query(miPath, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String paths = cursor.getString(columnIndex);
                    cursor.close();
                    img = paths;
                    photoURI=miPath;
                    img = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "." + extension(photoURI);
                    cargaArchivo();
                    // bitmap = BitmapFactory.decodeFile(path);
                    //  imgProducto.setImageBitmap(bitmap);
                }

                break;
            case COD_FOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap imageBitmapNuevo = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);

                    String tituliImge = "IMG_" + System.currentTimeMillis();
                    String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), imageBitmapNuevo, tituliImge, "Foto de la app de mascotas");
                    //muestra la imagen
                    photoURI = Uri.parse(path);

                    ivImagen.setImageBitmap(imageBitmapNuevo);
                    //img = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures" + "/" + tituliImge + ".jpg";

                    img = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "." + extension(photoURI);
                    cargaArchivo();
                }
                break;
        }
    }
    private String extension(Uri photoUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(photoUri));
    }
    private void cargaArchivo() {
        img = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "." + extension(photoURI);
        ///////
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                StorageReference ref = storageReference.child(img);
                System.out.println(ref.toString());
                ref.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(),"Imagen cargada exitosamente",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Algo salio mal al cargar el archivo\n"+e.getCause(),Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                StorageReference ref = storageReference.child(img);
                ref.putFile(photoURI);
                Toast.makeText(getContext(),"Imagen cargada exitosamente\nEstado: sin conexión",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }

    }
    public void mostrarDialogOpciones() {
        CharSequence[] opciones = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar foto")) {
                    abrirCamera();
                    //  Toast.makeText(getContext(), "Cargar camara...", Toast.LENGTH_SHORT).show();
                } else if (opciones[i].equals("Elegir de galeria")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(Intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                } else {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EliminarProductosViewModel.class);
        // TODO: Use the ViewModel
    }

}