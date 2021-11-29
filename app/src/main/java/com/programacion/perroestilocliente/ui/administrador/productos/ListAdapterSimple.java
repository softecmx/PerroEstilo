package com.programacion.perroestilocliente.ui.administrador.productos;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Categorias;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;

import java.util.ArrayList;

public class ListAdapterSimple extends ArrayAdapter<ElementListView> {

    private Activity activity;
    private ArrayList<ElementListView> arrayList;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String img="";
    private Context context;

    public ListAdapterSimple(Activity activity, ArrayList<ElementListView> arrayList,Context context) {
        super(activity, R.layout.item_lista_producto, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        this.context = context;
        iniciaFirebase();
    }

    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_lista_producto, parent, false);
        }
        ImageView foto = convertView.findViewById(R.id.ivListProdImg);
        TextView codigo= convertView.findViewById(R.id.tvListProdCod);
        TextView diseño = convertView.findViewById(R.id.tvListaProductoDisenio);
        TextView talla = convertView.findViewById(R.id.tvListaProductoTalla);
        ImageView star1= convertView.findViewById(R.id.ivLisProdStar1);
        ImageView star2= convertView.findViewById(R.id.ivLisProdStar2);
        ImageView star3= convertView.findViewById(R.id.ivLisProdStar3);
        ImageView star4= convertView.findViewById(R.id.ivLisProdStar4);
        ImageView star5= convertView.findViewById(R.id.ivLisProdStar5);
        TextView descuento = convertView.findViewById(R.id.tvListProdDescuento);
        TextView precio = convertView.findViewById(R.id.tvListProdPrecio);
        img = arrayList.get(position).getImgFoto();
        cargaImagen(foto,img);
        codigo.setText(arrayList.get(position).getNombre());

        databaseReference.child("Disenios").orderByChild("idModelo").equalTo(arrayList.get(position).getDisenio()).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                Disenios c=null;
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    c = objSnapshot.getValue(Disenios.class);
                }
                if (c!=null){
                    diseño.setText(c.getDisenio());
                }else{

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        databaseReference.child("Tallas").orderByChild("idTalla").equalTo(arrayList.get(position).getTalla()).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                Tallas c=null;
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    c = objSnapshot.getValue(Tallas.class);
                }
                if (c!=null){
                    talla.setText(c.getTallas());
                }else{

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
        float estrella = Float.parseFloat(arrayList.get(position).getRaiting());
        estrella = (float) Math.round(estrella);
        String est=""+estrella;
        System.out.println(est+" promedio");
        switch (est){
            case "0":
                break;
            case "1.0":
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case "2.0":
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case "3.0":
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case "4.0":
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case "5.0":
                star1.setImageResource(R.drawable.ic_star_black_24dp);
                star2.setImageResource(R.drawable.ic_star_black_24dp);
                star3.setImageResource(R.drawable.ic_star_black_24dp);
                star4.setImageResource(R.drawable.ic_star_black_24dp);
                star5.setImageResource(R.drawable.ic_star_black_24dp);
                break;
        }
        descuento.setText("Descuento: "+arrayList.get(position).getDescuento()+"%");
        precio.setText("Precio: $"+arrayList.get(position).getPrecioVenta());
        return convertView;
    }
    private void cargaImagen(ImageView ivFoto, String img) {
        storageReference.child(img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(ivFoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Ups! Ha ocurrido un erro al recuperar la imagen\n" + e.getCause(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ElementListView getItem(int index) {
        return arrayList.get(index);
    }

}
