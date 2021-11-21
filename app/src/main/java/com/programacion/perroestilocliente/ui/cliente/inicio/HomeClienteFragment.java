package com.programacion.perroestilocliente.ui.cliente.inicio;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.databinding.ItemCustomFixedSizeLayout4Binding;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeClienteFragment extends Fragment {

    private HomeClienteViewModel mViewModel;

    ImageCarousel carousel;
    List<CarouselItem> list = new ArrayList<>();
    View root;

    ImageCarousel carouselComentarios;
    ViewFlipper viewFlipper;

    List<CarouselItem> listComentarios = new ArrayList<>();

    public static HomeClienteFragment newInstance() {
        return new HomeClienteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home_cliente, container, false);


        //====================================================================
        carousel = root.findViewById(R.id.carruselInicio);
        list.add(new CarouselItem(R.drawable.img_carrucel_admin1));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin2));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin3));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin4));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin5));

        carousel.setData(list);
        //=====================================================================

        carouselComentarios = root.findViewById(R.id.carruselComentarios);

        List<Integer> colorsForSix = new ArrayList<>();
        colorsForSix.add(R.color.flat_awesome_green_1);
        colorsForSix.add(R.color.flat_green_1);
        colorsForSix.add(R.color.flat_blue_1);
        colorsForSix.add(R.color.flat_pink_1);
        colorsForSix.add(R.color.flat_yellow_1);
        colorsForSix.add(R.color.flat_orange_1);

        HashMap<Integer, String> map = new HashMap<>();
        map.put(1,"Lorem ipsum");
        map.put(2,"Cum sociis");
        map.put(3,"Euismod lacinia");
        map.put(4,"Tincidunt ornare");
        map.put(5,"Pellentesque elit");
        map.put(0,"Semper eget");
        HashMap<Integer, String> map2 = new HashMap<>();
        map2.put(0, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ultrices gravida dictum fusce ut placerat.");
        map2.put(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ultrices gravida dictum fusce ut placerat.");
        map2.put(2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ultrices gravida dictum fusce ut placerat.");
        map2.put(3, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ultrices gravida dictum fusce ut placerat.");
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

                int currentColor = ResourcesCompat.getColor(getResources(), colorsForSix.get(position), null);
                currentBinding.card.setCardBackgroundColor(currentColor);

                currentBinding.tvCaption.setText("-"+map.get(position));
                currentBinding.tvBody.setText(map2.get(position));
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

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeClienteViewModel.class);
        // TODO: Use the ViewModel
    }

}