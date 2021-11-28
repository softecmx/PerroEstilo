package com.programacion.perroestilocliente.ui.administrador.productos.editarProductos;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.programacion.perroestilocliente.ui.administrador.catalogo.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.catalogo.SpnAdapterCategoria;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class EditarProductosFragment extends Fragment
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    private EditarProductosViewModel mViewModel;
    EditText etId;
    private ImageView ivImagen;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fbFoto;
    private AutoCompleteTextView categoria, disenio, talla,spinAgProdStat;
    private EditText descripcion,costo,precio,decuento,stock,nombre;
    private Button editar,limpia;
    View root;
    ImageButton busca;

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

    String img = "",idCat="",idDis="",idTal="",id="";
    public static EditarProductosFragment newInstance() {
        return new EditarProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_editar_productos, container, false);
        cargar();
        llenaSpn();
        iniciaFirebase();
        listar();
        listener();
        return root;
    }
    private void llenaSpn(){
        ArrayAdapter<CharSequence> generoAdapter;
        generoAdapter = ArrayAdapter.createFromResource(getContext(),R.array.estatus_producto, android.R.layout.simple_spinner_item);
        spinAgProdStat.setAdapter(generoAdapter);
        spinAgProdStat.setOnItemClickListener(this);
    }
    public void cargar(){
        etId=root.findViewById(R.id.etEPid);
        ivImagen=root.findViewById(R.id.imgEdPfoto);
        fbFoto=root.findViewById(R.id.fBtnEdPfoto);
        categoria=root.findViewById(R.id.spinEPcategoria);
        disenio=root.findViewById(R.id.spinEPdisenio);
        talla=root.findViewById(R.id.spinEPtalla);
        spinAgProdStat=root.findViewById(R.id.spinEditaProdstatus);
        descripcion=root.findViewById(R.id.editEPDescripcion);
        costo=root.findViewById(R.id.etEPCosProd);
        precio=root.findViewById(R.id.etEPPrecProd);
        decuento=root.findViewById(R.id.etEPPDesc);
        stock=root.findViewById(R.id.etEPPStock);
        nombre=root.findViewById(R.id.etEditaProductoNombre);
        editar=root.findViewById(R.id.btnEPModificar);
        limpia=root.findViewById(R.id.btnAddProdLimpiar);
        busca=root.findViewById(R.id.ibtnEdPbsc);

        editar.setOnClickListener(new View.OnClickListener() {
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
        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });
    }
    public void buscar(){
        id = etId.getText().toString();
        if (id.equals("")){
            CustomToast.mostarToast("Ingresa un ID",2,false,root,getLayoutInflater(),getContext());
        }else{
            databaseReference.child("Productos").orderByChild("idProducto").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Productos c = null;
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        c = objSnapshot.getValue(Productos.class);
                    }
                    if (c != null) {
                        nombre.setText(c.getNombre());
                        costo.setText(c.getCosto());
                        precio.setText(c.getPrecioVenta());
                        decuento.setText(c.getDescuento());
                        stock.setText(c.getStock());
                        spinAgProdStat.setText(c.getEstatusStock());
                        llenaSpn();
                        descripcion.setText(c.getDescripcion());
                        img=c.getImgFoto();
                        cargaImagen(ivImagen, img);
                        etId.setEnabled(false);

                        databaseReference.child("Disenios").orderByChild("idModelo").equalTo(c.getDisenio()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Disenios c = null;
                                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                    c = objSnapshot.getValue(Disenios.class);
                                }
                                if (c != null) {
                                    idDis=c.getIdModelo();
                                    disenio.setText(c.getDisenio());
                                    disenio.setAdapter(customAdapterDisenio);
                                    customAdapterDisenio = new com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapterSimple (getActivity(), arrayList2,getContext());
                                    disenio.setAdapter(customAdapterDisenio);
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
                                    idTal=c.getIdTalla();
                                    talla.setText(c.getTallas());
                                    customAdapterTalla = new com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple(getActivity(), arrayList3);
                                    talla.setAdapter(customAdapterTalla);
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
                                    idCat=c.getIdCategorias();
                                    categoria.setText(c.getNombreCategoria().toString());
                                    customAdapter = new SpnAdapterCategoria(getActivity(), arrayList);
                                    categoria.setAdapter(customAdapter);
                                } else {

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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
    public void valida(){
        if (id.equals("")||nombre.getText().toString().equals("")||descripcion.getText().toString().equals("") || costo.getText().toString().equals("")||precio.getText().toString().equals("")||
                decuento.getText().toString().equals("") || stock.getText().toString().equals("") ||
                img.equals("")||spinAgProdStat.getText().toString().equals("")||idCat.equals("")||idDis.equals("")||idTal.equals("")){
            CustomToast.mostarToast("Faltan datos por agregar",2,false,root,getLayoutInflater(),getContext());
        }else {
            Productos prod = new Productos(id,nombre.getText().toString(), idCat, idDis, idTal, precio.getText().toString(), costo.getText().toString(), decuento.getText().toString(), new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), "0", spinAgProdStat.getText().toString(), stock.getText().toString(), "1", img,descripcion.getText().toString());
            databaseReference.child("Productos").child(prod.getIdProducto()).setValue(prod);
            CustomToast.mostarToast("Dato editado!",1,false,root,getLayoutInflater(),getContext());
            limpiar();
        }
    }
    public void limpiar(){
        etId.setEnabled(true);
        etId.setText("");
        ivImagen.setImageResource(R.drawable.no_image);
        categoria.setText("");
        disenio.setText("");
        talla.setText("");
        listar();
        spinAgProdStat.setText("");
        llenaSpn();
        descripcion.setText("");
        costo.setText("");
        precio.setText("");
        decuento.setText("");
        stock.setText("");
        nombre.setText("");
        img = "";
        idCat="";idDis="";
        idTal="";
        id="";
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    public void listener(){
        categoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idCat=customAdapter.getItem(position).getId();
            }
        });
        disenio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idDis=customAdapterDisenio.getItem(position).getIdModelo();
            }
        });
        talla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idTal=customAdapterTalla.getItem(position).getId();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarProductosViewModel.class);
        // TODO: Use the ViewModel
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
                    customAdapterDisenio = new com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapterSimple (getActivity(), arrayList2,getContext());
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

    }
}