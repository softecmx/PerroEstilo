package com.programacion.perroestilocliente.ui.cliente.ayuda;

import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.programacion.perroestilocliente.R;

import java.util.ArrayList;

public class AyudaFragment extends Fragment {

    private AyudaViewModel mViewModel;
    Button btnAyuda;
    public static AyudaFragment newInstance() {
        return new AyudaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_ayuda, container, false);
       btnAyuda=root.findViewById(R.id.btnAyudaAyuda);
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent setIntent=new Intent();
               /* setIntent.setAction(Intent.ACTION_SEND);
                setIntent.putExtra(Intent.EXTRA_TEXT,"Holas mensaje");
                setIntent.setType("text/plain");
                setIntent.setPackage("com.whatsapp");*/
                setIntent.setAction(Intent.ACTION_VIEW);
                String uri="whatsapp://send?phone="+52+"7131037910"+"&text="+"Hola, vengo de la aplicación y necesito ayuda...";
                setIntent.setData(Uri.parse(uri));
                startActivity(setIntent);

            }
        });
        return root;
    }
    public void sendToWhatsApp(View view, String message, Bitmap[] photos) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.whatsapp");

        if (!TextUtils.isEmpty(message))
            intent.putExtra(Intent.EXTRA_TEXT, message);

        if (photos != null && photos.length > 0) {
            intent.setType("image/*");

            ArrayList<Uri> imageUriArray = new ArrayList<>();

            for (Bitmap bitmap : photos) {
                String path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(), bitmap, "Título", null);
                Uri imageUri = Uri.parse(path);
                imageUriArray.add(imageUri);
            }

            intent.putExtra(android.content.Intent.EXTRA_STREAM, imageUriArray);
        } else
            intent.setType("text/plain");

        try {
            view.getContext().startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Snackbar.make(view, "El dispositivo no tiene instalado WhatsApp", Snackbar.LENGTH_LONG)
                    .show();
        }
    }
    /*private void abrirWhatsApp(String telefono){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, "Scientia Soluciones Informáticas - http://www.scientia.com.ar");

        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();

            Snackbar.make(getView(), "El dispositivo no tiene instalado WhatsApp", Snackbar.LENGTH_LONG)
                    .show();
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AyudaViewModel.class);
        // TODO: Use the ViewModel
    }

}