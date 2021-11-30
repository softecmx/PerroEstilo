package com.programacion.perroestilocliente.ui.administrador.disenios.consularDisenios;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.disenios.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapter;
import com.programacion.perroestilocliente.ui.administrador.productos.ListAdapterSimple;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ConsultarDiseniosFragment extends Fragment {

    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    public static final int REQUEST_PERMISSION_CAMERA = 100;

    private Uri photoURI;

    private ConsultarDiseniosViewModel mViewModel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;

    LayoutInflater inflaterLayout;


    private Button btnPopCerrar;
    private Button btnPopAgregar;
    private EditText etPopNombre;
    private ImageView ivPopImg;
    com.google.android.material.floatingactionbutton.FloatingActionButton fbPopFoto;

    private ImageButton busca;
    private EditText etNombre;
    View root;
    private ListView listView;
    private androidx.appcompat.app.AlertDialog dialog;
    com.google.android.material.floatingactionbutton.FloatingActionButton fBtnAgregar;
    private ListAdapter customAdapter;
    ArrayList<ElementListView> arrayList;

    String img="";

    public static ConsultarDiseniosFragment newInstance() {
        return new ConsultarDiseniosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_consultar_disenios, container, false);
        etNombre = root.findViewById(R.id.etBDisenioid);
        listView = root.findViewById(R.id.listdisenios);
        fBtnAgregar = root.findViewById(R.id.fButtonAddDisenios);
        busca = root.findViewById(R.id.ibtnADiseniobsc);

        iniciaFirebase();

        listarElementos();

        registerForContextMenu(listView);
        fBtnAgregar.setOnClickListener(view -> createDialogAgregar());
        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busca();
            }
        });
        return root;
    }
    public void busca(){
        if (etNombre.getText().toString().equals("")){
            Toast.makeText(getContext(),"Ingrese un dato",Toast.LENGTH_SHORT).show();
        }else{
            ArrayList<ElementListView> arrayBusca = new ArrayList<>();
            for (int i = 0;i<arrayList.size();i++){
                if (arrayList.get(i).getDisenio().toUpperCase().equals(etNombre.getText().toString().toUpperCase())){
                    arrayBusca.add(arrayList.get(i));
                }
            }
            customAdapter = new ListAdapter(getActivity(), arrayBusca,getLayoutInflater(),getContext(),root);
            listView.setAdapter(customAdapter);
        }
    }
    public void listarElementos(){
        databaseReference.child("Disenios").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Disenios p = objSnapshot.getValue(Disenios.class);
                    arrayList.add(new ElementListView(p.getIdModelo(),p.getDisenio(),p.getEstadoLogico(), p.getImagen()));
                    customAdapter = new ListAdapter(getActivity(), arrayList,getLayoutInflater(),getContext(),root);
                    listView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void createDialogAgregar(){
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = getLayoutInflater().inflate(R.layout.dialog_disenio, null);

        btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnAgrDisenioCancelar);
        btnPopAgregar = (Button) aboutPop.findViewById(R.id.btnAgrDisenioAceptar);
        etPopNombre = (EditText) aboutPop.findViewById(R.id.etADisenioNom);
        ivPopImg =(ImageView) aboutPop.findViewById(R.id.imgAddDisenioFoto);
        fbPopFoto =  aboutPop.findViewById(R.id.fBtnAddDisenioFoto);

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();

        fbPopFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
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
                    disenio.setIdModelo(UUID.randomUUID().toString());
                    databaseReference.child("Disenios").child(disenio.getIdModelo()).setValue(disenio);
                    Toast.makeText(getContext(), "Datos registrado!", Toast.LENGTH_SHORT).show();

                    etPopNombre.setText("");
                    ivPopImg.setImageResource(R.drawable.no_image);
                    img = "";
                    dialog.dismiss();
                }
            }
        });
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Disenios");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarDiseniosViewModel.class);
        // TODO: Use the ViewModel
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
                    ivPopImg.setImageURI(miPath);
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

                    ivPopImg.setImageBitmap(imageBitmapNuevo);
                    //img = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures" + "/" + tituliImge + ".jpg";

                    img = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "." + extension(photoURI);
                    cargaArchivo();
                }
                break;
        }
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


    public void mostarToast(String txt, int estatus, boolean corto) {
        try {
            LayoutInflater inflater = this.inflaterLayout;
            View layout = null;
            if(estatus==0){
                //Default
                layout = inflater.inflate(R.layout.custom_toast_info,
                        (ViewGroup) root.findViewById(R.id.layout_base));

            }else if(estatus==1){
                //Success
                layout = inflater.inflate(R.layout.custom_toast_success,
                        (ViewGroup) root.findViewById(R.id.layout_base));

            }else if(estatus==2) {
                //Warning
                layout = inflater.inflate(R.layout.custom_toast_warning,
                        (ViewGroup) root.findViewById(R.id.layout_base));

            }else if(estatus==3){
                //Error
                layout = inflater.inflate(R.layout.custom_toast_error,
                        (ViewGroup) root.findViewById(R.id.layout_base));


            }else if(estatus==4){
                //Falla de red
                layout = inflater.inflate(R.layout.custom_toast_red,
                        (ViewGroup) root.findViewById(R.id.layout_base));


            }else if(estatus==5){
                //Falla de red
                layout = inflater.inflate(R.layout.custom_toast_sin_data,
                        (ViewGroup) root.findViewById(R.id.layout_base));


            }else{
                //Informacion
                layout = inflater.inflate(R.layout.custom_toast_info,
                        (ViewGroup) root.findViewById(R.id.layout_base));
            }

            TextView textView = layout.findViewById(R.id.txtToast);
            textView.setText(txt);
            Toast toast = new Toast(getContext());
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 50);
            if(corto){
                toast.setDuration(Toast.LENGTH_SHORT);}else{
                toast.setDuration(Toast.LENGTH_LONG);}
            toast.setView(layout);
            toast.show();}catch (Exception e){

        }
    }
    private String extension(Uri photoUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(photoUri));
    }
}