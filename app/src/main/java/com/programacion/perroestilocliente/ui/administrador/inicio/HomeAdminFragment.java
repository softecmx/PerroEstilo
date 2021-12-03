package com.programacion.perroestilocliente.ui.administrador.inicio;

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
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Persona;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Usuarios;
import com.programacion.perroestilocliente.ui.administrador.inventario.ListAdapterInventario;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends Fragment {

    private HomeAdminViewModel mViewModel;

    TextView nombre;
    ImageCarousel carousel;

    //Elementos del list view
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
/*
        databaseReference.child("Usuarios/Tienda").orderByChild("email").equalTo("admin@perroestilo.com.mx").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                Usuarios u=null;
                OrdenesCliente pedidos= null;
                Productos produ=null;
                Persona persona= null;
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    u = objSnapshot.getValue(Usuarios.class);
                    pedidos= objSnapshot.getValue(OrdenesCliente.class);
                    //produ= objSnapshot.getValue(Productos.class);
                    persona= objSnapshot.getValue(Persona.class);
                }
                if (u!=null){
                    nombre.setText("!Bienvenida " + u.getUsername()+" !");
                    //stock.setText("Productos con pocas existencias... "+produ.getStock());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        */
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
    ArrayList<ElementListViewInicioAdmin> arrayList2 = new ArrayList<>();
    private void listarPedidos() {
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