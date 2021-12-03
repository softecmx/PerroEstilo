package com.programacion.perroestilocliente.ui.cliente.tienda;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.cliente.inicio.ListAdapterCategorias;
import com.programacion.perroestilocliente.ui.cliente.inicio.ReciclerViewAdapterProductos;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TiendaFragment extends Fragment {

    private TiendaViewModel mViewModel;

    ImageCarousel carousel;
    List<CarouselItem> list = new ArrayList<>();
    View root;
    androidx.recyclerview.widget.RecyclerView reciclerViewHomeNuevosProductos;
    ArrayList<Productos> arrayListProductos = new ArrayList<>();
    private ReciclerViewAdapterProductos adapterProductos;
    HashMap<String, Productos> hashProductos = new HashMap<>();

    ListView lstProductosModa;
    List<Productos> lstProdModa = new ArrayList<>();
    ListAdapterProductosModa listAdapterProductosModa;

    androidx.cardview.widget.CardView cardViewCatMasc;
    androidx.cardview.widget.CardView cardViewCatHumanos;
    androidx.cardview.widget.CardView cardViewCatMaths;
    androidx.cardview.widget.CardView cardViewTiendaTemporada;

    Button  btnTiendaOfertas;
    Button  btnTiendaModa;
    Button  btnTiendaKITS;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    public static TiendaFragment newInstance() {
        return new TiendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tienda, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        //====================================================================
        carousel = root.findViewById(R.id.carruselTienda);
        list.add(new CarouselItem(R.drawable.img_carrucel_admin1));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin2));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin3));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin4));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin5));

        carousel.setData(list);
        //=====================================================================


        //===========================================================================================
        reciclerViewHomeNuevosProductos = root.findViewById(R.id.reciclerViewTiendaProductos);
/*
        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListProductos.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Productos productos = objSnapshot.getValue(Productos.class);
                    arrayListProductos.add(productos);
                    adapterProductos = new ReciclerViewAdapterProductos(root.getContext(), arrayListProductos, getFragmentManager());

                    reciclerViewHomeNuevosProductos.setLayoutManager(new StaggeredGridLayoutManager(2,
                            StaggeredGridLayoutManager.VERTICAL));
                    reciclerViewHomeNuevosProductos.setAdapter(adapterProductos);
                    reciclerViewHomeNuevosProductos.setAdapter(adapterProductos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
*/

        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListProductos.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Productos productos = objSnapshot.getValue(Productos.class);
                    boolean nuevo=false;
                    try {
                        String fecha=productos.getFechaCreacion();

                        int anio=Integer.parseInt(fecha.substring(0,4));
                        int mes=Integer.parseInt(fecha.substring(4,6));
                        int dia=Integer.parseInt(fecha.substring(6,7));

                        Calendar c = Calendar.getInstance();
                        int anioC = c.get(Calendar.YEAR);
                        int mesC = c.get(Calendar.MONTH);
                        int diaC = c.get(Calendar.DAY_OF_MONTH);

                        int anios = anioC - anio;
                        int meses = mesC - mes;
                        int semanas = (diaC - dia) / 7;
                        if ((anios) >= 1) {
                           nuevo=false;
                        } else {
                            if (meses < 1) {
                                nuevo=true;
                            } else if (meses == 1) {
                                nuevo=true;
                            } else {
                                nuevo=false;
                            }
                        }

                    }catch (Exception e){
                        System.out.println("Trono en la fecha");
                    }
                 if(nuevo==true){
                     arrayListProductos.add(productos);
                     adapterProductos = new ReciclerViewAdapterProductos(root.getContext(), arrayListProductos, getFragmentManager());

                     reciclerViewHomeNuevosProductos.setLayoutManager(new StaggeredGridLayoutManager(2,
                             StaggeredGridLayoutManager.VERTICAL));
                     reciclerViewHomeNuevosProductos.setAdapter(adapterProductos);
       /* reciclerViewHomeNuevosProductos.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));*/
                     reciclerViewHomeNuevosProductos.setAdapter(adapterProductos);
                 }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        lstProductosModa=root.findViewById(R.id.lstTiendaProductosModa);


        databaseReference.child("Productos").orderByChild("raiting").equalTo("5.0").limitToFirst(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstProdModa.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Productos productos = objSnapshot.getValue(Productos.class);
                    lstProdModa.add(productos);
                    listAdapterProductosModa = new ListAdapterProductosModa(getActivity(), lstProdModa);
                    lstProductosModa.setAdapter(listAdapterProductosModa);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        cardViewCatMasc=root.findViewById(R.id.cardViewCatMasc);
        cardViewCatHumanos=root.findViewById(R.id.cardViewCatHumanos);
        cardViewCatMaths=root.findViewById(R.id.cardViewCatMaths);
        cardViewTiendaTemporada=root.findViewById(R.id.cardViewTiendaTemporada);

        btnTiendaOfertas=root.findViewById(R.id.btnTiendaOfertas);
        btnTiendaModa=root.findViewById(R.id.btnTiendaModa);
        btnTiendaKITS=root.findViewById(R.id.btnTiendaKITS);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TiendaViewModel.class);
        // TODO: Use the ViewModel
    }

}