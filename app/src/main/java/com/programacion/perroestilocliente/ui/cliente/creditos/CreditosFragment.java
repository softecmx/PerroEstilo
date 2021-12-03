package com.programacion.perroestilocliente.ui.cliente.creditos;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programacion.perroestilocliente.MainActivity;
import com.programacion.perroestilocliente.R;

public class CreditosFragment extends Fragment {

    private CreditosViewModel mViewModel;
    private String urlkevinGit = "https://github.com/KevinSanchez-Cat";
    private String urlAnaGit = "https://github.com/anasual";
    private String urlCarlosGit = "https://github.com/estetipo";
    public static CreditosFragment newInstance() {
        return new CreditosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_creditos, container, false);
        CardView cardKevin=root.findViewById(R.id.carviewKevin);
        CardView cardCarlos=root.findViewById(R.id.carviewCarlos);
        CardView cardAna=root.findViewById(R.id.carviewAna);
        TextView version=root.findViewById(R.id.txtAcercaVersion);
        version.setText(MainActivity.version);
        cardKevin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(urlkevinGit);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardCarlos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(urlCarlosGit);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        cardAna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(urlAnaGit);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreditosViewModel.class);
        // TODO: Use the ViewModel
    }

}