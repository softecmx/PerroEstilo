package com.programacion.perroestilocliente.ui.ajustes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class AjustesFragment extends Fragment {

    private ListView listView;
    private ListAdapter customAdapter;

    View root;
    private AjustesViewModel mViewModel;

    public static AjustesFragment newInstance() {
        return new AjustesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_ajustes, container, false);

        // Inflate the layout for this fragment
        this.listView = root.findViewById(R.id.listAjustes);

        ArrayList<ElementListView> arrayList = new ArrayList<>();
        arrayList.add(new ElementListView("Recibir notificaciones cuando existan nuevos productos", "",true));
        arrayList.add(new ElementListView("Recibir correos cuando existan nuevos productos", "",true));
        arrayList.add(new ElementListView("Recibir notificaciones en alta prioridad", "",true));
        arrayList.add(new ElementListView("Vibración", "",true));
        arrayList.add(new ElementListView("Luz", "",true));
        arrayList.add(new ElementListView("Sonido", "Sonido",false));
        arrayList.add(new ElementListView("Tono de notificación", "Sonido",false));

        this.customAdapter = new ListAdapter(getActivity(), arrayList);
        this.listView.setAdapter(customAdapter);
        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AjustesViewModel.class);
        // TODO: Use the ViewModel
    }

}