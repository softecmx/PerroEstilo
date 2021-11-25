package com.programacion.perroestilocliente.ui.administrador.productos.agregarProductos;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.catalogo.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.catalogo.SpnAdapterCategoria;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class AgregarProductosFragment extends Fragment {

    private ImageView ivImagen;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fbFoto;
    private AutoCompleteTextView categoria, disenio, talla;
    private EditText descripcion,costo,precio,decuento,stock,estatus;
    private Button agregar,limpia;
    View root;

    private SpnAdapterCategoria customAdapter;
    private com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapterSimple customAdapterDisenio;
    private com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple customAdapterTalla;

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    public static final int REQUEST_PERMISSION_CAMERA = 100;

    private Uri photoURI;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;

    ArrayList<ElementListView> arrayList;
    ArrayList<com.programacion.perroestilocliente.ui.administrador.disenios.ElementListView> arrayList2;
    ArrayList<com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView> arrayList3;

    String img = "";

    private AgregarProductosViewModel mViewModel;

    public static AgregarProductosFragment newInstance() {
        return new AgregarProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_agregar_productos, container, false);
        cargar();
        iniciaFirebase();
        listar();

        return root;
    }
    public void cargar(){
        ivImagen = root.findViewById(R.id.imgAddProdFoto);
        fbFoto = root.findViewById(R.id.fBtnAddProdFoto);

        categoria  = root.findViewById(R.id.spinAPcategoria);
        disenio = root.findViewById(R.id.spinAPdisenio);
        talla = root.findViewById(R.id.spinAPtalla);

        descripcion = root.findViewById(R.id.editAddAddProdDescripcion);
        costo = root.findViewById(R.id.etAPCosProd);
        precio = root.findViewById(R.id.etAPPrecProd);
        decuento = root.findViewById(R.id.etAPDesc);
        stock = root.findViewById(R.id.etAPStock);
        agregar = root.findViewById(R.id.btnAddProdAgregar);
        limpia = root.findViewById(R.id.btnAddProdLimpiar);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valida();
            }
        });
        limpia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
        fbFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });
    }
    public void valida(){
        if (descripcion.getText().toString().equals("") || costo.getText().toString().equals("")||precio.getText().toString().equals("")||
                decuento.getText().toString().equals("") || stock.getText().toString().equals("") || categoria.getText().toString().equals("")||
                disenio.getText().toString().equals("") || talla.getText().toString().equals("")||img.equals("")){
            CustomToast.mostarToast("Faltan datos por agregar",2,false,root,getLayoutInflater(),getContext());
        }else {
            databaseReference.child("Categorias").orderByChild("idCategorias").equalTo(categoria.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Productos prod = new Productos(UUID.randomUUID().toString(), categoria.getText().toString(), disenio.getText().toString(), talla.getText().toString(), precio.getText().toString(), costo.getText().toString(), decuento.getText().toString(), new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), "0", "1", stock.getText().toString(), "1", img);
                    System.out.println(categoria.getText().toString()+"  categoria");
                    Categorias c=null;
                    for (DataSnapshot objSnapshot: snapshot.getChildren()){
                        c = objSnapshot.getValue(Categorias.class);
                    }
                    if (c!=null){
                        if (c.getArr()==null){
                            c.setArr(new ArrayList<Productos>());
                        }
                        c.getArr().add(prod);
                        databaseReference.child("Categorias").child(c.getIdCategorias()).setValue(c);
                    }
                    Toast.makeText(getContext(), "Datos registrado!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            limpiar();
        }
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
    public void limpiar(){
        img="";
        ivImagen.setImageResource(R.drawable.no_image);
        categoria.setText("");
        disenio.setText("");
        talla.setText("");

        descripcion.setText("");
        costo.setText("");
        precio.setText("");
        decuento.setText("");
        stock.setText("");
    }
    public void listar(){
        databaseReference.child("Categorias").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList = new ArrayList<>();
                categoria.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Categorias p = objSnapshot.getValue(Categorias.class);
                    arrayList.add(new ElementListView(p.getIdCategorias(),p.getNombreCategoria(),p.getTipoPublico(), p.getEstadoLogico(), p.getDescripcion()));
                    customAdapter = new SpnAdapterCategoria(getActivity(), arrayList);
                    categoria.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        //
        databaseReference.child("Disenios").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList2 = new ArrayList<>();
                disenio.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Disenios p = objSnapshot.getValue(Disenios.class);
                    arrayList2.add(new com.programacion.perroestilocliente.ui.administrador.disenios.ElementListView(p.getIdModelo(), p.getDisenio(), p.getEstadoLogico(), p.getImagen()));
                    customAdapterDisenio = new com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapterSimple (getActivity(), arrayList2);
                    disenio.setAdapter(customAdapterDisenio);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        //
        databaseReference.child("Tallas").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList3 = new ArrayList<>();
                talla.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Tallas p = objSnapshot.getValue(Tallas.class);
                    arrayList3.add(new com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView(p.getTallas(), p.getMedidas(), p.getIdTalla(), p.getEstadoLogico()));
                    customAdapterTalla = new com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple(getActivity(), arrayList3);
                    talla.setAdapter(customAdapterTalla);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
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
        mViewModel = new ViewModelProvider(this).get(AgregarProductosViewModel.class);
        // TODO: Use the ViewModel
    }

}