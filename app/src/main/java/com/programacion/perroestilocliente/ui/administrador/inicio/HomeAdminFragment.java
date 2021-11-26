package com.programacion.perroestilocliente.ui.administrador.inicio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.modelo.Persona;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Usuarios;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends Fragment {

    private HomeAdminViewModel mViewModel;

    TextView nombre,stock;
    ImageCarousel carousel;
    //Fisebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    List<CarouselItem> list = new ArrayList<>();
    View root;
    public static HomeAdminFragment newInstance() {
        return new HomeAdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
     root = inflater.inflate(R.layout.fragment_home_admin, container, false);
     nombre= root.findViewById(R.id.tvBienvenidaAdmin);
     stock = root.findViewById(R.id.textViewcantidadInicioAProductos);
     firebaseDatabase = FirebaseDatabase.getInstance();
     databaseReference = firebaseDatabase.getReference();


        carousel = root.findViewById(R.id.carruselInicioAdmin);

        list.add(new CarouselItem(R.drawable.img_carrucel_admin1));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin2));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin3));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin4));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin5));

        carousel.setData(list);

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
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeAdminViewModel.class);
        // TODO: Use the ViewModel
    }

}