package com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView;
import com.programacion.perroestilocliente.ui.cliente.comprar.comprarAhora.ComprarAhoraFragment;

import java.util.ArrayList;

public class VerProductoTiendaFragment extends Fragment {
    private com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple customAdapterTalla;
    ArrayList<ElementListView> arrayList3;
    String idTal="";
    private AutoCompleteTextView talla;
    private VerProductoTiendaViewModel mViewModel;
    View root;
    TextView txtNombre;
    TextView txtPrecio;
    TextView txtdescuento;
    TextView txtPrecioAntiguo;
    TextView txtdesc;
    RatingBar ratingBar;
    ImageView img;
    Button btnAgregar;
    Button btnAyuda;
    Button btnComprarAhora;
    Button btnmas;
    Button btnmenos;
    EditText cantidad;
    String id;
    String imagen;
    Float precioPublico;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public static VerProductoTiendaFragment newInstance() {
        return new VerProductoTiendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ver_producto_tienda, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        Bundle arg = getArguments();
        id= arg.getString("idProducto");
       // System.out.println(id+" VER PRODUCTOS");
        talla=root.findViewById(R.id.spinVerProdTalla);
        txtNombre = root.findViewById(R.id.txtVerProdNombre);
        txtPrecio = root.findViewById(R.id.txtVerProdPrecio);
        ratingBar = root.findViewById(R.id.raitingBarVerProd);
        img = root.findViewById(R.id.imgVerProd);
        btnAgregar = root.findViewById(R.id.btnVerProdAgregarCar);
        btnAyuda = root.findViewById(R.id.btnVerProdPreguntar);
        btnComprarAhora = root.findViewById(R.id.btnVerProdComprarAhora);
        btnmenos = root.findViewById(R.id.btnVerProdMenos);
        btnmas = root.findViewById(R.id.btnVerProdMas);
        txtdescuento = root.findViewById(R.id.txtVerProdDescuento);
        cantidad = root.findViewById(R.id.editVerProdCantidad);
        talla = root.findViewById(R.id.spinVerProdTalla);
        txtdesc = root.findViewById(R.id.txtVerProdDesc);
        txtPrecioAntiguo=root.findViewById(R.id.txtVerProdPrecioAntiguo);
        listar();
        listener();
        Query queryMascota = databaseReference.child("Productos").orderByChild("idProducto").equalTo(id);
        queryMascota.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        Productos productos = objSnapshot.getValue(Productos.class);
                        txtNombre.setText(productos.getNombre());
                        imagen=productos.getImgFoto();

                        txtPrecio.setText(productos.getPrecioVenta());
                        ratingBar.setRating(Float.parseFloat(productos.getRaiting()));
                        storageReference.child(productos.getImgFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(root).load(uri).into(img);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
                            }
                        });
                        txtdescuento.setText(productos.getDescuento());
                        txtdesc.setText(productos.getDescripcion());
                        String raiting = productos.getRaiting();
                        float raitingStar = 0;
                        if (raiting.isEmpty()) {
                            raitingStar = 0;
                        } else {
                            raitingStar = Float.parseFloat(raiting);
                        }
                       ratingBar.setRating(raitingStar);

                        if (productos.getDescuento().equals("") || productos.getDescuento().equals("0")) {
                            txtdescuento.setVisibility(View.GONE);
                            txtPrecioAntiguo.setVisibility(View.GONE);
                            txtPrecio.setText("$" + productos.getPrecioVenta());
                            precioPublico=Float.parseFloat(productos.getPrecioVenta());
                        } else {
                            txtdescuento.setVisibility(View.VISIBLE);
                            float descuento = Float.parseFloat(productos.getDescuento());

                            float precio = Float.parseFloat(productos.getPrecioVenta());
                            float descuentoReal = (descuento * precio) / 100;
                            float precioActual = precio - descuentoReal;
                            float redondeo = Math.round(precioActual);
                            txtPrecioAntiguo.setText(productos.getPrecioVenta());
                            precioPublico=redondeo;
                            txtPrecioAntiguo.setPaintFlags(txtPrecioAntiguo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            txtPrecioAntiguo.setText("$" + productos.getPrecioVenta());
                            txtPrecio.setText("$" + redondeo);
                            txtdescuento.setText("Descuento del: "+productos.getDescuento()+"%");
                        }




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cantidad.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Cantidad invalida",Toast.LENGTH_SHORT).show();
                    cantidad.setText("1");
                }else{
                    int cant=0;
                    try {
                        cant=Integer.parseInt(cantidad.getText().toString());
                        if(cant==1){
                            cantidad.setText("1");
                        }else if(cant>1){
                            int restaCantidad=cant-1;
                            cantidad.setText(restaCantidad+"");
                        }
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Número invalido",Toast.LENGTH_SHORT).show();
                        cantidad.setText("1");
                    }

                }
            }
        });
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Intent setIntent=new Intent();
               /* setIntent.setAction(Intent.ACTION_SEND);
                setIntent.putExtra(Intent.EXTRA_TEXT,"Holas mensaje");
                setIntent.setType("text/plain");
                setIntent.setPackage("com.whatsapp");*/
                    setIntent.setAction(Intent.ACTION_VIEW);
                    String uri="whatsapp://send?phone="+52+"7131037910"+"&text="+"Hola, vengo de la aplicación y queria preguntarte sobre el producto "+txtNombre.getText().toString();
                    setIntent.setData(Uri.parse(uri));
                    startActivity(setIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    ex.printStackTrace();

                    Snackbar.make(getView(), "El dispositivo no tiene instalado WhatsApp", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idTal.isEmpty()){
                    Toast.makeText(getContext(),"No ha seleccionado la talla",Toast.LENGTH_LONG).show();
                }else{
                    if(cantidad.getText().toString().isEmpty()){
                        Toast.makeText(getContext(),"La cantidad no es aceptable",Toast.LENGTH_LONG).show();
                    }else{
                        int cant;
                        try {
                            cant=Integer.parseInt(cantidad.getText().toString());
                            if(cant>=1){
                               agregarCarro();
                            }else{
                                Toast.makeText(getContext(),"La cantidad no es valida",Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"La cantidad no es valida",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        btnComprarAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idTal.isEmpty()){
                    Toast.makeText(getContext(),"No ha seleccionado la talla",Toast.LENGTH_LONG).show();
                }else{
                    if(cantidad.getText().toString().isEmpty()){
                        Toast.makeText(getContext(),"La cantidad no es aceptable",Toast.LENGTH_LONG).show();
                    }else{
                        int cant;
                        try {
                            cant=Integer.parseInt(cantidad.getText().toString());
                            if(cant>=1){
                               agregarCarro();
                                ComprarAhoraFragment newFragment1 = new ComprarAhoraFragment();
                                Bundle args = new Bundle();
                                newFragment1.setArguments(args);
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }else{
                                Toast.makeText(getContext(),"La cantidad no es valida",Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"La cantidad no es valida",Toast.LENGTH_LONG).show();
                        }
                    }
                }


            }
        });
        btnmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cantidad.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Cantidad invalida",Toast.LENGTH_SHORT).show();
                    cantidad.setText("1");
                }else{
                    int cant=0;
                    try {
                        cant=Integer.parseInt(cantidad.getText().toString().trim());
                        if(cant>=1){
                            int sumaCantidad=cant+1;
                            cantidad.setText(sumaCantidad+"");
                        }
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Número invalido",Toast.LENGTH_SHORT).show();
                        cantidad.setText("1");
                    }

                }
            }
        });


        return root;
    }

    private void agregarCarro(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        Query findCarrito = ref.child(String.format("Carrito/%s/Items", user.getUid())).orderByChild("producto").equalTo(id);
        findCarrito.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                Item item = objSnapshot.getValue(Item.class);
                                Item nvoItem = new Item();
                                nvoItem.setIdUsuario(user.getUid());
                                nvoItem.setProducto(id);
                                nvoItem.setImg(item.getImg());
                                nvoItem.setTalla(idTal);
                                nvoItem.setPrecio(item.getPrecio());
                                nvoItem.setCantidad(item.getCantidad()+Integer.parseInt(cantidad.getText().toString()));
                                ref.child("Carrito/" + user.getUid() + "/Items").child(item.getProducto()).setValue(nvoItem).addOnSuccessListener(aVoid -> {
                                }).addOnFailureListener(e -> {
                                });
                            }
                        } else {
                            Item item = new Item();
                            item.setProducto(id);
                            item.setIdUsuario(user.getUid());
                            item.setCantidad(Integer.parseInt(cantidad.getText().toString()));
                            item.setTalla(idTal);
                            item.setPrecio(precioPublico);

                            item.setImg(imagen);
                            ref.child("Carrito/" + user.getUid() + "/Items").child(item.getProducto()).setValue(item).addOnSuccessListener(aVoid -> {
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerProductoTiendaViewModel.class);
        // TODO: Use the ViewModel
    }
    public void listar(){
        databaseReference.child("Tallas").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList3 = new ArrayList<>();
                talla.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Tallas p = objSnapshot.getValue(Tallas.class);
                    arrayList3.add(new com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView(p.getTallas(), p.getMedidas(), p.getIdTalla(), p.getEstadoLogico()));
                    customAdapterTalla = new com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple(getActivity(), arrayList3);
                    talla.setAdapter(customAdapterTalla);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
    }
    public void listener(){
        talla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idTal=customAdapterTalla.getItem(position).getId();
            }
        });
    }

}