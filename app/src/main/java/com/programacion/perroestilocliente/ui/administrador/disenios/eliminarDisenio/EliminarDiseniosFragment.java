package com.programacion.perroestilocliente.ui.administrador.disenios.eliminarDisenio;

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
import android.widget.AutoCompleteTextView;
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
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.ui.administrador.disenios.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EliminarDiseniosFragment extends Fragment {
    private Button btnElimina,btnLimpiar;
    private TextView tvNombre;
    private ImageView ivPopImg;
    com.google.android.material.floatingactionbutton.FloatingActionButton fbPopFoto;
    private AutoCompleteTextView disenio;
    private ImageButton buscar;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    public static final int REQUEST_PERMISSION_CAMERA = 100;

    private Uri photoURI;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;

    private ListAdapter customAdapter;
    private com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapterSimple customAdapterSimple;
    ArrayList<ElementListView> arrayList;

    String img="";
    ElementListView idDis = null;

    View root;

    private EliminarDiseniosViewModel mViewModel;

    public static EliminarDiseniosFragment newInstance() {
        return new EliminarDiseniosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_eliminar_disenios, container, false);
        btnElimina = root.findViewById(R.id.btnFrameEliminarDisenioAceptar);
        btnLimpiar = root.findViewById(R.id.btnFrameEliminarDisenioLimpiar);
        tvNombre = root.findViewById(R.id.tvEliminaDisenioNombre);
        ivPopImg = root.findViewById(R.id.imgFrameEliminarDisenioFoto);
        disenio = root.findViewById(R.id.spnEliminarDisenioID);
        buscar = root.findViewById(R.id.btnEliminarDiseniobusca);

        iniciaFirebase();

        listarElementos();

        disenio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idDis=arrayList.get(position);
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valida();
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busca();
            }
        });

        return root;
    }

    public void busca(){
        if (idDis==null){
            CustomToast.mostarToast("Seleccione un diseño",2,false,root,getLayoutInflater(),getContext());
        }else{
            tvNombre.setText(" "+idDis.getDisenio());
            img = idDis.getImagen();
            cargaImagen(ivPopImg,img);
            disenio.setAdapter(null);
            buscar.setEnabled(false);
            disenio.setEnabled(false);
        }
    }
    public void valida(){
        if (idDis==null){
            CustomToast.mostarToast("Selecicone un dato",2,false,root,getLayoutInflater(),getContext());
        }else{
            databaseReference.child("Disenios").child(idDis.getIdModelo()).removeValue();
            Toast.makeText(getContext(), "Datos modificados!", Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    public void limpiar(){
        buscar.setEnabled(true);
        disenio.setEnabled(true);
        tvNombre.setText("");
        disenio.setText("");
        ivPopImg.setImageResource(R.drawable.no_image);
        img="";
        idDis=null;
        listarElementos();
    }
    public void listarElementos(){
        databaseReference.child("Disenios").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList = new ArrayList<>();
                disenio.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Disenios p = objSnapshot.getValue(Disenios.class);
                    arrayList.add(new com.programacion.perroestilocliente.ui.administrador.disenios.ElementListView(p.getIdModelo(), p.getDisenio(), p.getEstadoLogico(), p.getImagen()));
                    customAdapterSimple = new com.programacion.perroestilocliente.ui.administrador.disenios.ListAdapterSimple (getActivity(), arrayList,getContext());
                    disenio.setAdapter(customAdapterSimple);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){
            }
        });
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
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Disenios");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
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
    private String extension(Uri photoUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(photoUri));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EliminarDiseniosViewModel.class);
        // TODO: Use the ViewModel
    }

}