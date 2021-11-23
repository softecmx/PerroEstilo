package com.programacion.perroestilocliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import com.programacion.perroestilocliente.ui.cliente.mainCliente.NavClienteActivity;

public class MainActivity extends AppCompatActivity {

    public static String version;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Perfiles");
        mAuth = FirebaseAuth.getInstance();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = String.valueOf(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //Para mostrar en toda la pantalla
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        ImageView logo = findViewById(R.id.imgVMainLogo);

        TextView title = findViewById(R.id.txtMainTitle);
        title.setAnimation(animation2);
        logo.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                if (user != null || account != null) {
                    //checar el nivel de usuario
                    if(user!=null){
                        Query queryAdmin = databaseReference.child("Usuarios/Tienda").orderByChild("email").equalTo(user.getEmail());
                        queryAdmin.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Intent intentAdmin = new Intent(MainActivity.this, NavAdministradorActivity.class);
                                    startActivity(intentAdmin);
                                    finish();
                                } else {
                                    Query queryClientes = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(user.getEmail());
                                    queryClientes.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Intent intentCliente = new Intent(MainActivity.this, NavClienteActivity.class);
                                                startActivity(intentCliente);
                                                finish();
                                            } else {
                                                Intent intentCliente = new Intent(MainActivity.this, LogginActivity.class);
                                                startActivity(intentCliente);
                                                finish();
                                                //  mostarToast("Error de autenticación",3,true);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else if(account!=null){
                        Query queryAdmin = databaseReference.child("Usuarios/Tienda").orderByChild("email").equalTo(account.getEmail());
                        queryAdmin.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Intent intentAdmin = new Intent(MainActivity.this, NavAdministradorActivity.class);
                                    startActivity(intentAdmin);
                                    finish();
                                } else {
                                    Query queryClientes = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(account.getEmail());
                                    queryClientes.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Intent intentCliente = new Intent(MainActivity.this, NavClienteActivity.class);
                                                startActivity(intentCliente);
                                                finish();
                                            } else {
                                                Intent intentCliente = new Intent(MainActivity.this, LogginActivity.class);
                                                startActivity(intentCliente);
                                                finish();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        Intent intent = new Intent(MainActivity.this, LogginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    ///
                } else {
                    Intent intent = new Intent(MainActivity.this, LogginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);
    }
    public void mostarToast(String txt, int estatus, boolean corto) {
        LayoutInflater inflater = getLayoutInflater();

        View layout = null;

        if(estatus==0){
            //Default
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) findViewById(R.id.layout_base));

        }else if(estatus==1){
            //Success
            layout = inflater.inflate(R.layout.custom_toast_success,
                    (ViewGroup) findViewById(R.id.layout_base));

        }else if(estatus==2) {
            //Warning
            layout = inflater.inflate(R.layout.custom_toast_warning,
                    (ViewGroup) findViewById(R.id.layout_base));

        }else if(estatus==3){
            //Error
            layout = inflater.inflate(R.layout.custom_toast_error,
                    (ViewGroup) findViewById(R.id.layout_base));


        }else if(estatus==4){
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_red,
                    (ViewGroup) findViewById(R.id.layout_base));


        }else if(estatus==5){
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_sin_data,
                    (ViewGroup) findViewById(R.id.layout_base));


        }else{
            //Informacion
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) findViewById(R.id.layout_base));
        }

        TextView textView = layout.findViewById(R.id.txtToast);
        textView.setText(txt);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 50);
        if(corto){
            toast.setDuration(Toast.LENGTH_SHORT);}else{
            toast.setDuration(Toast.LENGTH_LONG);}
        toast.setView(layout);
        toast.show();
    }
}