package com.programacion.perroestilocliente.ui.cliente.mainCliente;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.databinding.ActivityNavClienteBinding;
import com.programacion.perroestilocliente.modelo.Administrador;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.comprar.comprarAhora.ComprarAhoraFragment;
import com.programacion.perroestilocliente.ui.cliente.tienda.TiendaFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NavClienteActivity extends AppCompatActivity {


    public static ArrayList<Item> lstCarrito;

    public static String id = "";
    public static String nombre = "";
    public static String precio = "";
    public static String img = "";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavClienteBinding binding;
    private androidx.appcompat.app.AlertDialog dialog;
    private Button btnPopCerrar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FragmentManager mFraFragmentManager;
    Clientes usuarios;
    TextView txtSubtitulo;
    TextView txtTitulo;
    ImageView imgUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavCliente.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ajustes, R.id.nav_ayuda, R.id.nav_categoria, R.id.nav_producto, R.id.nav_pedidos, R.id.nav_mi_cuenta, R.id.nav_mis_direcciones, R.id.nav_tienda,
                R.id.nav_sobre_nosotros, R.id.nav_acerca_app,
                R.id.nav_politicas, R.id.nav_ajustes, R.id.nav_salir, R.id.nav_mi_carrito)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.container_cliente);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        lstCarrito = new ArrayList<>();
        View navHeader = navigationView.getHeaderView(0);
        txtTitulo=navHeader.findViewById(R.id.txtTituloNavCliente);
        txtSubtitulo=navHeader.findViewById(R.id.txtSubtituloNavCliente);
        imgUser=navHeader.findViewById(R.id.imgNavCliente);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_carrito) {
            miCarrito();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_cliente, menu);
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Perfiles");
        Query queryCliente = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(user.getEmail());
        queryCliente.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        usuarios = objSnapshot.getValue(Clientes.class);
                        //  usuario = usuario.getNombreCliente() + " " + usuario.getApellidoPaterno();
                        txtTitulo.setText(usuarios.getNombreCliente()+" "+usuarios.getApellidoPaterno());
                        txtSubtitulo.setText(usuarios.getEmail());

                        if(usuarios.getFotoPerfil().contains("facebook")){
                            if (user.getPhotoUrl() != null) {

                                //cImgFoto.setImageURI(user.getPhotoUrl());
                                String pholoURL=user.getPhotoUrl().toString();
                                pholoURL=pholoURL+"?type=large";
                                Picasso.get().load(pholoURL)
                                        .error(R.drawable.logo)
                                        .into(imgUser);
                            } else {
                                imgUser.setImageResource(R.drawable.logo);
                            }
                        }else if(usuarios.getFotoPerfil().contains("googleusercontent")){
                            if (user.getPhotoUrl() != null) {
                                //cImgFoto.setImageURI(user.getPhotoUrl());
                                Picasso.get().load(user.getPhotoUrl())
                                        .error(R.drawable.logo)
                                        .into(imgUser);
                            } else {
                                imgUser.setImageResource(R.drawable.logo);
                            }
                        }else if(!usuarios.getFotoPerfil().isEmpty()){
                            if (getApplication() != null) {
                                storageReference.child(usuarios.getFotoPerfil()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(NavClienteActivity.this).load(uri).into(imgUser);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mostarToast(e.getCause() + "", 3, false);
                                    }
                                });
                            }
                        }else{
                            imgUser.setImageResource(R.drawable.logo);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container_cliente);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    float total = 0;
    Button btnComprarAhora;
    private void miCarrito() {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(NavClienteActivity.this);
        final View aboutPop = getLayoutInflater().inflate(R.layout.dialog_mi_carrito, null);
        // btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnCerrarDialog);
        TextView txtTotal = aboutPop.findViewById(R.id.txtDCarritoTotal);
        TextView txtProductos = aboutPop.findViewById(R.id.txtDCarritoAunSinComprar);
        btnComprarAhora= aboutPop.findViewById(R.id.btnDMiCarritoComprarAhora);
        Button btnContunuarComprando = aboutPop.findViewById(R.id.btnDMiCarritoContinuar);

        ListView reciclerViewMiCarritoProductos = aboutPop.findViewById(R.id.lstViewMiCarritoProductos);
        ArrayList<Item> arrayListItems = new ArrayList<>();
        total = 0;
        databaseReference.child("Carrito/" + user.getUid() + "/Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListItems.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        Item item = objSnapshot.getValue(Item.class);
                        arrayListItems.add(item);
                        ListAdapterCarrito adapterProductos = new ListAdapterCarrito(NavClienteActivity.this, arrayListItems);
                        reciclerViewMiCarritoProductos.setAdapter(adapterProductos);
                        total = total + (item.getPrecio() * item.getCantidad());
                        txtTotal.setText("$" + total);
                        txtProductos.setVisibility(View.GONE);
                    }
                } else {
                    txtTotal.setText("$0.0");
                    txtProductos.setVisibility(View.VISIBLE);
                    btnComprarAhora.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnComprarAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remplaza();
                dialog.dismiss();

            }
        });
        btnContunuarComprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

      /*  btnPopCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
        //cerrar
    }

    private void remplaza(){
        ComprarAhoraFragment newFragment1 = new ComprarAhoraFragment();
        Bundle args = new Bundle();
        newFragment1.setArguments(args);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_cliente, newFragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    private static Productos productos = null;

    public void mostarToast(String txt, int estatus, boolean corto) {
        LayoutInflater inflater = getLayoutInflater();

        View layout = null;

        if (estatus == 0) {
            //Default
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) findViewById(R.id.layout_base));

        } else if (estatus == 1) {
            //Success
            layout = inflater.inflate(R.layout.custom_toast_success,
                    (ViewGroup) findViewById(R.id.layout_base));

        } else if (estatus == 2) {
            //Warning
            layout = inflater.inflate(R.layout.custom_toast_warning,
                    (ViewGroup) findViewById(R.id.layout_base));

        } else if (estatus == 3) {
            //Error
            layout = inflater.inflate(R.layout.custom_toast_error,
                    (ViewGroup) findViewById(R.id.layout_base));


        } else if (estatus == 4) {
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_red,
                    (ViewGroup) findViewById(R.id.layout_base));


        } else if (estatus == 5) {
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_sin_data,
                    (ViewGroup) findViewById(R.id.layout_base));


        } else {
            //Informacion
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) findViewById(R.id.layout_base));
        }

        TextView textView = layout.findViewById(R.id.txtToast);
        textView.setText(txt);
        Toast toast = new Toast(this);
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