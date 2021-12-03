package com.programacion.perroestilocliente.ui.administrador.inicio;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Administrador;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Persona;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Usuarios;
import com.programacion.perroestilocliente.ui.administrador.inventario.ListAdapterInventario;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeAdminFragment extends Fragment {

    private HomeAdminViewModel mViewModel;

    TextView nombre;
    ImageCarousel carousel;

    //Elementos del list view
    ImageView imgBienvenida;
    private TextView producto;
    private TextView cantidadStock;
    private TextView usuario;
    private ImageView imgUsuario;
    private ImageView imgProducto;
    private TextView cantidadProductos;
    private TextView cantidadPedidos;
    private TextView cantidadVentas;
    private TextView cantidadClientes;
    private TextView nuevosPedidos;
    private TextView pocosProductos;
    //Fisebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReferenceProductos;
    private StorageReference storageReferenceUsuario;
    List<CarouselItem> list = new ArrayList<>();
    View root;
    int contP=0;

    private ListView listViewProductos;
    private ListView listViewPedidos;
    private ListAdapterInicioAdmin customAdapter;
    private ListAdapterInicioAdminPedidos customAdapter1;
    public static HomeAdminFragment newInstance() {
        return new HomeAdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
     root = inflater.inflate(R.layout.fragment_home_admin, container, false);
     nombre= root.findViewById(R.id.tvBienvenidaAdmin);
     listViewProductos=root.findViewById(R.id.listaProductosHomeAdmin);
     listViewPedidos= root.findViewById(R.id.listaPedidosHomeAdmin);
     imgBienvenida=root.findViewById(R.id.imgBienvenida);
     firebaseDatabase = FirebaseDatabase.getInstance();
     databaseReference = firebaseDatabase.getReference();
     storageReferenceProductos = FirebaseStorage.getInstance().getReference("Productos");
     storageReferenceUsuario =FirebaseStorage.getInstance().getReference("Perfiles");

        carousel = root.findViewById(R.id.carruselInicioAdmin);
        list.add(new CarouselItem(R.drawable.img_carrucel_admin1));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin2));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin3));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin4));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin5));

        carousel.setData(list);

        databaseReference.child("Usuarios/Tienda").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Administrador tienda = objSnapshot.getValue(Administrador.class);
                    DateFormat df=new SimpleDateFormat("HH:mm");
                    String time=df.format(Calendar.getInstance().getTime());
                    DateFormat df1=new SimpleDateFormat("yyyy/MM/dd");
                    String date=df1.format(Calendar.getInstance().getTime());
                    Calendar cal = Calendar.getInstance();
                    String date2 = ""+cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
                    String hora = ""+cal.get(Calendar.HOUR_OF_DAY);
                    String time2 = ""+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
                   try {
                       int horaI=Integer.parseInt(hora);
                       if(horaI>=7 && horaI<=11){
                           nombre.setText("¡Buenos días " + tienda.getNombreAdministrador()+"!");
                           imgBienvenida.setImageResource(R.drawable.amanecer);
                       }else if(horaI>=12 && horaI<=7){
                           nombre.setText("¡Buenas tardes " + tienda.getNombreAdministrador()+"!");
                           imgBienvenida.setImageResource(R.drawable.dia);
                       }else{
                           nombre.setText("¡Buenas noches " + tienda.getNombreAdministrador()+"!");
                           imgBienvenida.setImageResource(R.drawable.noche);
                       }
                   }catch (Exception e){

                   }



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        estadisticas();
        listarPedidos();
        listarProductos();
        return root;
    }


    private void estadisticas() {
        cantidadProductos=root.findViewById(R.id.textViewcantidadInicioAProductos);
        cantidadPedidos =root.findViewById(R.id.textViewcantidadInicioAPedidos);
        cantidadVentas=root.findViewById(R.id.textViewcantidadInicioAVentas);
        cantidadClientes = root.findViewById(R.id.textViewcantidadInicioAClientes);
        nuevosPedidos=root.findViewById(R.id.textItemNuevosPedidos);
        pocosProductos = root.findViewById(R.id.textItemPocasExistencias);
    }
    private void listarPedidos() {

        databaseReference.child("OrdenesCliente/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewInicioAdmin> arrayList = new ArrayList<>();

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Log.i("ids clientes ", objSnapshot.getKey());
                    databaseReference.child("OrdenesCliente/"+objSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot2 : snapshot.getChildren()){
                                //Log.i("ids clientes ", objSnapshot2.getKey());
                                databaseReference.child("OrdenesCliente/"+objSnapshot.getKey()+"/"+objSnapshot2.getKey()+"/").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //Log.i("Ruta del nombre  ", "OrdenesCliente/"+objSnapshot.getKey()+"/"+objSnapshot2.getKey()+"/nombreContacto");
                                        String nombrecliente=snapshot.child("nombreContacto").getValue().toString();
                                        String idcliente=snapshot.child("idCliente").getValue().toString();

                                        //Log.i("Ruta a la imagen ", "Usuarios/Clientes/"+idcliente);

                                        llenarlistaPedidos(idcliente,nombrecliente);
                                   /* databaseReference.child("Usuarios/Clientes/"+objSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String fotocliente=snapshot.child("fotoPerfil").getValue().toString();
                                                    Log.i("USUARIO ", fotocliente);
                                                    contP++;
                                                    arrayList.add(new ElementListViewInicioAdmin(nombrecliente, fotocliente));
                                                    customAdapter1 = new ListAdapterInicioAdminPedidos(getActivity(), arrayList, getContext());
                                                    listViewPedidos.setAdapter(customAdapter1);
                                                /*
                                                String fotocliente=u.getFotoPerfil();
                                                //String fotocliente = snapshot.child("fotoPerfil").getValue().toString();
                                                Log.i("idCliente ", fotocliente);
                                        }
                                        @Override

        ArrayList<ElementListViewInicioAdmin> arrayList2 = new ArrayList<>();
            databaseReference.child("Usuarios/Clientes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList2.clear();
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        try {
                            Clientes usuarios = objSnapshot.getValue(Clientes.class);
                            databaseReference.child("OrdenesCliente/"+usuarios.getIdUsuario()).orderByChild("estatusOrden").equalTo("Preparando pedido").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    for (DataSnapshot objSnapshot2 : snapshot.getChildren()) {
                                        try {
                                            // OrdenesCliente ordenesCliente = objSnapshot.getValue(OrdenesCliente.class);
                                            contP++;
                                            arrayList2.add(new ElementListViewInicioAdmin(usuarios.getNombreCliente(), usuarios.getFotoPerfil()));
                                            customAdapter1 = new ListAdapterInicioAdminPedidos(getActivity(), arrayList2, getContext());
                                            listViewPedidos.setAdapter(customAdapter1);

                                        }catch (Exception e){
                                            Log.i("Hay error",e.getMessage()+" ssfsdf");

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }catch (Exception e){
                            Log.i("Hay error",e.getMessage()+"hahsad");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
    private void llenarlistaPedidos(String idcliente, String nombrecliente) {
        databaseReference.child("Usuarios/Clientes").child(idcliente+"/fotoPerfil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewInicioAdmin> arrayList = new ArrayList<>();

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    try {
                        Usuarios u = objSnapshot.getValue(Usuarios.class);

                        contP++;
                        arrayList.add(new ElementListViewInicioAdmin(nombrecliente, u.getFotoPerfil()));
                        customAdapter1 = new ListAdapterInicioAdminPedidos(getActivity(), arrayList, getContext());
                        listViewPedidos.setAdapter(customAdapter1);

                    }catch (Exception e){
                        Log.i("Hay error",e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listarProductos() {
        databaseReference.child("Productos").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewInicioAdmin> arrayList = new ArrayList<>();
                int cont=0;
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    try {
                        Productos p = objSnapshot.getValue(Productos.class);
                        if(Integer.parseInt(p.getStock())<= 5){
                            cont++;
                            arrayList.add(new ElementListViewInicioAdmin(p.getNombre() ,p.getStock(),p.getImgFoto()));
                            customAdapter = new ListAdapterInicioAdmin(getActivity(), arrayList,getContext());
                            listViewProductos.setAdapter(customAdapter);
                            cantidadProductos.setText(cont);
                        }
                   }catch (Exception e){
                        Log.i("error",e.getMessage());
                    }
                }
                pocosProductos.setText("Productos con pocas existencias ("+cont+")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeAdminViewModel.class);
        // TODO: Use the ViewModel
    }

}