package com.programacion.perroestilocliente.ui.cliente.tienda;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.programacion.perroestilocliente.R;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class TiendaFragment extends Fragment {

    private TiendaViewModel mViewModel;

    ImageCarousel carousel;
    List<CarouselItem> list = new ArrayList<>();
    View root;

    public static TiendaFragment newInstance() {
        return new TiendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    root= inflater.inflate(R.layout.fragment_tienda, container, false);

        //====================================================================
        carousel = root.findViewById(R.id.carruselTienda );
        list.add(new CarouselItem(R.drawable.img_carrucel_admin1));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin2));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin3));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin4));
        list.add(new CarouselItem(R.drawable.img_carrucel_admin5));

        carousel.setData(list);
        //=====================================================================
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TiendaViewModel.class);
        // TODO: Use the ViewModel
    }

}