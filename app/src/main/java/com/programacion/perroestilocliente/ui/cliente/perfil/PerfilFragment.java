package com.programacion.perroestilocliente.ui.cliente.perfil;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Administrador;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {

    ImageView cImgFoto;
    ImageView imgLealtad;
    private Button btnPopCerrar;
    private androidx.appcompat.app.AlertDialog dialog;
    private ListView listView;
    private ListAdapter customAdapter;
    View root;
    com.google.android.material.floatingactionbutton.FloatingActionButton fBtnFoto;
    //FOTOGRAFIA
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    private Uri photoURI;
    public static final int REQUEST_TAKE_PHOTO = 101;
    public static final int REQUEST_PERMISSION_CAMERA = 100;
    public static final int REQUEST_PERMISSION_WRITE_STORANGE = 200;
    public static String img = "";


    //Fisebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    ArrayList<ElementListView> arrayList = new ArrayList<>();
    String username = "";
    String email = "";
    String phone = "";
    Clientes usuario;
    private PerfilViewModel mViewModel;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_perfil, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Perfiles");

        cImgFoto = root.findViewById(R.id.imgPerfilFoto);

        fBtnFoto = root.findViewById(R.id.fBtnPerfilFoto);
        imgLealtad = root.findViewById(R.id.imgLealtad);
        this.listView = root.findViewById(R.id.listViewCuenta);


        fBtnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
            }
        });


        Query queryCliente = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(user.getEmail());
        queryCliente.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        usuario = objSnapshot.getValue(Clientes.class);
                        username = usuario.getNombreCliente() + " " + usuario.getApellidoPaterno();
                        email = usuario.getEmail();
                        phone = usuario.getTelefono();
                       if(usuario.getFotoPerfil().contains("facebook")){
                           if (user.getPhotoUrl() != null) {

                               //cImgFoto.setImageURI(user.getPhotoUrl());
                               String pholoURL=user.getPhotoUrl().toString();
                               pholoURL=pholoURL+"?type=large";
                               Picasso.get().load(pholoURL)
                                       .error(R.drawable.img_user_exist)
                                       .into(cImgFoto);
                           } else {
                               cImgFoto.setImageResource(R.drawable.img_user_exist);
                           }
                        }else if(usuario.getFotoPerfil().contains("googleusercontent")){
                           if (user.getPhotoUrl() != null) {
                               //cImgFoto.setImageURI(user.getPhotoUrl());
                               Picasso.get().load(user.getPhotoUrl())
                                       .error(R.drawable.img_user_exist)
                                       .into(cImgFoto);
                           } else {
                               cImgFoto.setImageResource(R.drawable.img_user_exist);
                           }
                        }else if(!usuario.getFotoPerfil().isEmpty()){
                           if (getActivity() != null) {
                               storageReference.child(usuario.getFotoPerfil()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       Glide.with(getContext()).load(uri).into(cImgFoto);
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       mostarToast(e.getCause() + "", 3, false);
                                   }
                               });
                           }
                        }else{
                           cImgFoto.setImageResource(R.drawable.img_user_exist);
                        }

                        if(usuario.getLealtad().equals("No")){
                            imgLealtad.setImageResource(R.drawable.ic_pets_grey_24dp);
                        }else{
                            imgLealtad.setImageResource(R.drawable.ic_pets_pink_24dp);
                        }
                        arrayList.clear();
                        arrayList.add(new ElementListView("Nombre", username, true, R.drawable.ic_person_black_24dp));
                        arrayList.add(new ElementListView("Email", email, false, R.drawable.ic_email_black_24dp));
                        arrayList.add(new ElementListView("Teléfono", phone, true, R.drawable.ic_phone_black_24dp));
                        customAdapter = new ListAdapter(getActivity(), arrayList);
                        listView.setAdapter(customAdapter);

                    }
                } else {
                    Query queryTienda = databaseReference.child("Usuarios/Tienda").orderByChild("email").equalTo(user.getEmail());
                    queryTienda.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                            Administrador usuario = objSnapshot.getValue(Administrador.class);
                                            username = usuario.getNombreAdministrador() + " " + usuario.getApellidoPaterno();
                                            email = usuario.getEmail();
                                            phone = usuario.getTelefono();

                                            if (usuario.getPassword().equals("Authenticacion por Google")) {
                                                if (user.getPhotoUrl() != null) {
                                                    //cImgFoto.setImageURI(user.getPhotoUrl());
                                                    Picasso.get().load(user.getPhotoUrl())
                                                            .error(R.drawable.img_user_exist)
                                                            .into(cImgFoto);
                                                } else {
                                                    cImgFoto.setImageResource(R.drawable.img_user_exist);
                                                }
                                            } else if (usuario.getPassword().equals("Authenticacion por Facebook")) {
                                                if (user.getPhotoUrl() != null) {

                                                    //cImgFoto.setImageURI(user.getPhotoUrl());
                                                    String pholoURL=user.getPhotoUrl().toString();
                                                    pholoURL=pholoURL+"?type=large";
                                                    Picasso.get().load(pholoURL)
                                                            .error(R.drawable.img_user_exist)
                                                            .into(cImgFoto);
                                                } else {
                                                    cImgFoto.setImageResource(R.drawable.img_user_exist);
                                                }
                                            } else {
                                                if (usuario.getFotoPerfil().isEmpty()) {
                                                    cImgFoto.setImageResource(R.drawable.img_user_exist);
                                                } else {
                                                    if (getActivity() != null) {
                                                        storageReference.child(usuario.getFotoPerfil()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                Glide.with(getContext()).load(uri).into(cImgFoto);
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                mostarToast(e.getCause() + "", 3, false);
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                            arrayList.clear();
                                            arrayList.add(new ElementListView("Nombre", username, false, R.drawable.ic_person_black_24dp));
                                            arrayList.add(new ElementListView("Email", email, false, R.drawable.ic_email_black_24dp));
                                            arrayList.add(new ElementListView("Teléfono", phone, false, R.drawable.ic_phone_black_24dp));
                                            customAdapter = new ListAdapter(getActivity(), arrayList);
                                            listView.setAdapter(customAdapter);

                                        }
                                    }else{
                                        if (user.getDisplayName() != null) {
                                            if (user.getDisplayName().isEmpty()) {
                                                username = user.getEmail();
                                            } else {
                                                username = user.getDisplayName();
                                            }
                                        } else {
                                            username = user.getEmail();
                                        }

                                        email = user.getEmail();
                                        //txtEmail.setText(user.getEmail());
                                        if (user.getPhoneNumber() != null) {
                                            if (user.getPhoneNumber().isEmpty()) {
                                                phone = "Sin telefono";
                                            } else {
                                                phone = user.getPhoneNumber();
                                            }

                                        } else {
                                            phone = "Sin telefono";
                                        }
                                        if (user.getPhotoUrl() != null) {
                                            //cImgFoto.setImageURI(user.getPhotoUrl());
                                            Picasso.get().load(user.getPhotoUrl())
                                                    .error(R.drawable.img_user_exist)
                                                    .into(cImgFoto);
                                        } else {
                                            cImgFoto.setImageResource(R.drawable.img_user_exist);
                                        }

                                        arrayList.clear();
                                        arrayList.add(new ElementListView("Nombre", username, true, R.drawable.ic_person_black_24dp));
                                        arrayList.add(new ElementListView("Email", email, false, R.drawable.ic_email_black_24dp));
                                        arrayList.add(new ElementListView("Teléfono", phone, true, R.drawable.ic_phone_black_24dp));
                                        customAdapter = new ListAdapter(getActivity(), arrayList);
                                        listView.setAdapter(customAdapter);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                 /*

*/
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
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
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
                if (data != null) {
                    Uri miPath = data.getData();
                    cImgFoto.setImageURI(miPath);
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContext().getContentResolver().query(miPath, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String paths = cursor.getString(columnIndex);
                    cursor.close();
                    img = paths;
                    photoURI = miPath;
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

                    cImgFoto.setImageBitmap(imageBitmapNuevo);
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
                ref.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mostarToast("Imagen cargada exitosamente", 1, true);
                        Map<String,Object> mapear=new HashMap<>();
                        mapear.put("Usuarios/Clientes/"+usuario.getIdUsuario()+"/fotoPerfil",img);
                        databaseReference.updateChildren(mapear);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mostarToast("Algo salio mal al cargar el archivo\n" + e.getCause() + "", 3, true);
                    }
                });
            } else {
                StorageReference ref = storageReference.child(img);
                ref.putFile(photoURI);
                mostarToast("Imagen cargada exitosamente\nEstado: sin conexión", 4, true);
                Map<String,Object> mapear=new HashMap<>();
                mapear.put("Usuarios/Clientes/"+usuario.getIdUsuario()+"/fotoPerfil",img);
                databaseReference.updateChildren(mapear);
            }
        } catch (Exception e) {

        }

//////

    }

    private String extension(Uri photoUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(photoUri));
    }

    public void mostarToast(String txt, int estatus, boolean corto) {
        LayoutInflater inflater = getLayoutInflater();

        View layout = null;

        if (estatus == 0) {
            //Default
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) root.findViewById(R.id.layout_base));

        } else if (estatus == 1) {
            //Success
            layout = inflater.inflate(R.layout.custom_toast_success,
                    (ViewGroup) root.findViewById(R.id.layout_base));

        } else if (estatus == 2) {
            //Warning
            layout = inflater.inflate(R.layout.custom_toast_warning,
                    (ViewGroup) root.findViewById(R.id.layout_base));

        } else if (estatus == 3) {
            //Error
            layout = inflater.inflate(R.layout.custom_toast_error,
                    (ViewGroup) root.findViewById(R.id.layout_base));


        } else if (estatus == 4) {
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_red,
                    (ViewGroup) root.findViewById(R.id.layout_base));


        } else if (estatus == 5) {
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_sin_data,
                    (ViewGroup) root.findViewById(R.id.layout_base));


        } else {
            //Informacion
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) root.findViewById(R.id.layout_base));
        }

        TextView textView = layout.findViewById(R.id.txtToast);
        textView.setText(txt);
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 50);
        if (corto) {
            toast.setDuration(Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.setView(layout);
        toast.show();
    }

}