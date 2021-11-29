package com.programacion.perroestilocliente.ui.cliente.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.databinding.ItemCustomFixedSizeLayout4Binding;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Productos;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeClienteFragment extends Fragment {

    private HomeClienteViewModel mViewModel;
    View root;
    ViewFlipper viewFlipper;

    ImageCarousel carousel;
    List<CarouselItem> list = new ArrayList<>();

    ImageCarousel carouselGaleria;
    List<CarouselItem> listGaleria = new ArrayList<>();

    ImageCarousel carouselComentarios;
    List<CarouselItem> listComentarios = new ArrayList<>();

    androidx.recyclerview.widget.RecyclerView recyclerViewCategorias;
    androidx.recyclerview.widget.RecyclerView reciclerViewHomeNuevosProductos;

    List<Categorias> lstCategorias = new ArrayList<>();
    ListAdapterCategorias listAdapterCateforias;
    HashMap<String, Categorias> hashCategorias = new HashMap<>();


    ArrayList<Productos> arrayListProductos = new ArrayList<>();
    private ReciclerViewAdapterProductos adapterProductos;
    HashMap<String, Productos> hashProductos = new HashMap<>();
    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


    public static HomeClienteFragment newInstance() {
        return new HomeClienteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home_cliente, container, false);

        iniciarFirebase();
        //====================================================================
        carousel = root.findViewById(R.id.carruselInicio);
        list.add(new CarouselItem(R.drawable.img_carrucel_admin1));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin2));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin3));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin4));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin5));

        carousel.setData(list);

        //=====================================================================
        carouselGaleria = root.findViewById(R.id.carrucelGaleria);
        listGaleria.add(new CarouselItem(R.drawable.img_galeria1));
        listGaleria.add(new CarouselItem(R.drawable.img_galeria2));
        listGaleria.add(new CarouselItem(R.drawable.img_galeria3));
        listGaleria.add(new CarouselItem(R.drawable.img_galeria4));
        listGaleria.add(new CarouselItem(R.drawable.img_galeria5));

        carouselGaleria.setData(listGaleria);

        //=====================================================================

        carouselComentarios = root.findViewById(R.id.carruselComentarios);

        List<Integer> colorsForSix = new ArrayList<>();
        colorsForSix.add(R.color.flat_grey_normal_1);
        colorsForSix.add(R.color.flat_grey_normal_1);
        colorsForSix.add(R.color.flat_grey_normal_1);
        colorsForSix.add(R.color.flat_grey_normal_1);
        colorsForSix.add(R.color.flat_grey_normal_1);
        colorsForSix.add(R.color.flat_grey_normal_1);

        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "Juan Perez");
        map.put(1, "Monica Juarez");
        map.put(2, "Cum sociis");
        map.put(3, "Euismod lacinia");
        map.put(4, "Tincidunt ornare");
        map.put(5, "Pellentesque elit");

        HashMap<Integer, String> map2 = new HashMap<>();
        map2.put(0, "Los mejores precios, la mejor calidad.");
        map2.put(1, "Me han gustado los diseños. Estan padrisísimos");
        map2.put(2, "Ahora mi perrita tiene a ser más feliz con lo que lleva puesto.");
        map2.put(3, "Estoy feliz de encontrar una tienda en la que sus productos se ajusten a la medida de los clietnes");
        map2.put(4, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ultrices gravida dictum fusce ut placerat.");
        map2.put(5, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ultrices gravida dictum fusce ut placerat.");


        carouselComentarios.setCarouselListener(new CarouselListener() {
            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup parent) {
                // ...
                return ItemCustomFixedSizeLayout4Binding.inflate(layoutInflater, parent, false);
            }

            @Override
            public void onBindViewHolder(@NotNull ViewBinding binding, @NotNull CarouselItem item, int position) {
                // Convierte el enlace a la clase de enlace de vista devuelta del método onCreateViewHolder ().
                ItemCustomFixedSizeLayout4Binding currentBinding = (ItemCustomFixedSizeLayout4Binding) binding;
                try {
                    int currentColor = ResourcesCompat.getColor(getResources(), colorsForSix.get(position), null);
                    currentBinding.card.setCardBackgroundColor(currentColor);

                    currentBinding.tvCaption.setText("-" + map.get(position));
                    currentBinding.tvBody.setText(map2.get(position));
                } catch (Exception e) {

                }

            }

            @Override
            public void onLongClick(int position, @NotNull CarouselItem carouselItem) {
                // ...
            }

            @Override
            public void onClick(int position, @NotNull CarouselItem carouselItem) {
                // ...
            }
        });

        listComentarios.add(new CarouselItem());
        listComentarios.add(new CarouselItem());
        listComentarios.add(new CarouselItem());
        listComentarios.add(new CarouselItem());
        listComentarios.add(new CarouselItem());
        listComentarios.add(new CarouselItem());
        carouselComentarios.setData(listComentarios);

        //=============================================================================

        recyclerViewCategorias = root.findViewById(R.id.reciclerViewHomeCategorias);
        recyclerViewCategorias.setLayoutManager(new GridLayoutManager(recyclerViewCategorias.getContext(), 1,
                GridLayoutManager.HORIZONTAL, false));

        databaseReference.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstCategorias.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Categorias categoria = objSnapshot.getValue(Categorias.class);
                    lstCategorias.add(categoria);
                    listAdapterCateforias = new ListAdapterCategorias(lstCategorias, root.getContext());
                    recyclerViewCategorias.setHasFixedSize(true);
                    //recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(root.getContext()));
                    recyclerViewCategorias.setAdapter(listAdapterCateforias);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //===========================================================================================
        reciclerViewHomeNuevosProductos = root.findViewById(R.id.reciclerViewHomeNuevosProductos);

        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListProductos.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    Productos productos = objSnapshot.getValue(Productos.class);
                    arrayListProductos.add(productos);
                    adapterProductos = new ReciclerViewAdapterProductos(root.getContext(), arrayListProductos,getFragmentManager());

                    reciclerViewHomeNuevosProductos.setLayoutManager(new GridLayoutManager(reciclerViewHomeNuevosProductos.getContext(), 1,
                            GridLayoutManager.HORIZONTAL, false));
       /* reciclerViewHomeNuevosProductos.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));*/
                    reciclerViewHomeNuevosProductos.setAdapter(adapterProductos);
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
        mViewModel = new ViewModelProvider(this).get(HomeClienteViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    private void iniciarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
    }
}