package com.programacion.perroestilocliente.ui.cliente.carrito.miCarrito;


import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.modelo.Tallas;
import com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapterCarrito extends ArrayAdapter<Item> {
    LayoutInflater layoutInflater;
    Context context;
    View root;
    private Activity activity;
    private ArrayList<Item> arrayList;


    String idTalla = "";
    com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple customAdapterTalla;
    String tallaDatabase="";
    //  TextView txtCarritoModelo = convertView.findViewById(R.id.txtCarritoModelo);
    //  TextView txtCarritoTalla = convertView.findViewById(R.id.txtCarritoTalla);
    //  Button btnEdit;
    //  Button btnQuitar;
    private androidx.appcompat.app.AlertDialog dialog;

    public ListAdapterCarrito(Activity activity, ArrayList<Item> arrayList, LayoutInflater layoutInflater, Context context, View root) {
        super(activity, R.layout.item_prod_lista_carrito_editar, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;
        this.layoutInflater = layoutInflater;
        this.context = context;
        this.root = root;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.item_prod_lista_carrito_editar, parent, false);
        }


        TextView txtNombreProductoCarrito = convertView.findViewById(R.id.txtCarritoEditNombre);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView txtCarritoCandidad = convertView.findViewById(R.id.editMiCarrEditCantidad);
        TextView txtCarritoSubtotal = convertView.findViewById(R.id.txtCarritoEditSubtotal);
        TextView txtCarritoPrecioUnitario = convertView.findViewById(R.id.txtCarritoPrecioEditUnitario);
        ImageView imgCarrito = convertView.findViewById(R.id.imgCarritoEditFoto);

        Button btnEditar = convertView.findViewById(R.id.btnMiCarrEditEditar);
        Button btnVer = convertView.findViewById(R.id.btnMiCarrEditVer);
        Button btnQuitar = convertView.findViewById(R.id.btnMiCarrEditQuit);
        txtCarritoPrecioUnitario.setText("$" + arrayList.get(position).getPrecio());
        txtCarritoSubtotal.setText("Subtotal: $" + String.valueOf(arrayList.get(position).getPrecio() * arrayList.get(position).getCantidad()));

        storageReference.child(arrayList.get(position).getImg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(imgCarrito);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        txtCarritoCandidad.setText(String.valueOf(arrayList.get(position).getCantidad()));
        databaseReference.child("Productos").orderByChild("idProducto").equalTo(arrayList.get(position).getProducto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        Productos item = objSnapshot.getValue(Productos.class);
                        txtNombreProductoCarrito.setText(item.getNombre());
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edita(position);
            }
        });
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ver(position);
            }
        });
        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Carrito/" + user.getUid() + "/Items").child(arrayList.get(position).getProducto()).removeValue();
            }
        });

        return convertView;
    }

    public void edita(int position) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_editar_carrito, null);
        TextView txtNombreEditCar = aboutPop.findViewById(R.id.txtVerProdEditCarEditNombre);
        TextView txtPrecioEditCar = aboutPop.findViewById(R.id.txtVerProdEditCarEditPrecio);
        TextView txtDescEditCar = aboutPop.findViewById(R.id.txtVerProdEditEditCarDesc);
        AutoCompleteTextView spinTall = aboutPop.findViewById(R.id.spinVerProdEditCarEditTalla);
        Button btnEditar = aboutPop.findViewById(R.id.btnEditCarEditEditar);
        Button btnMas = aboutPop.findViewById(R.id.btnVerProdEditEditCarMas);
        Button btnMenos = aboutPop.findViewById(R.id.btnVerProdEditCarEditMenos);
        EditText cantidadEditCar = aboutPop.findViewById(R.id.editVerProdEditCarEditCantidad);
        Button btnCerrar = aboutPop.findViewById(R.id.btnEditCarEditCancelar);
        ImageView imgEditCar = aboutPop.findViewById(R.id.imgVerEditCarEditProd);
        txtPrecioEditCar.setText("$" + arrayList.get(position).getPrecio());
        cantidadEditCar.setText("" + arrayList.get(position).getCantidad());
        TextView txtSubtotal = aboutPop.findViewById(R.id.txtVerProdEditCarEditSubtotal);
        //  etPopNombre.setText(arrayList.get(position).getDisenio());


        float subT = Float.parseFloat(String.valueOf(arrayList.get(position).getCantidad() * arrayList.get(position).getPrecio()));
        txtSubtotal.setText("$" + subT + "");
        storageReference.child(arrayList.get(position).getImg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(imgEditCar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child("Productos").orderByChild("idProducto").equalTo(arrayList.get(position).getProducto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        Productos item = objSnapshot.getValue(Productos.class);
                        txtNombreEditCar.setText(item.getNombre());
                        txtDescEditCar.setText(item.getDescripcion());
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference.child("Tallas").orderByChild("estadoLogico").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListView> arrayList3 = new ArrayList<>();
                spinTall.setAdapter(null);
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Tallas p = objSnapshot.getValue(Tallas.class);
                    arrayList3.add(new com.programacion.perroestilocliente.ui.administrador.tallas.ElementListView(p.getTallas(), p.getMedidas(), p.getIdTalla(), p.getEstadoLogico()));
                    customAdapterTalla = new com.programacion.perroestilocliente.ui.administrador.tallas.ListAdapterSimple(activity, arrayList3);
                    spinTall.setAdapter(customAdapterTalla);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idTalla.isEmpty()) {
                    Toast.makeText(getContext(), "No ha seleccionado la talla", Toast.LENGTH_LONG).show();
                } else {
                    if (cantidadEditCar.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "La cantidad no es aceptable", Toast.LENGTH_LONG).show();
                    } else {
                        int cant;
                        try {
                            cant = Integer.parseInt(cantidadEditCar.getText().toString());
                            if (cant >= 1) {
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                assert user != null;
                                Query findCarrito = ref.child(String.format("Carrito/%s/Items", user.getUid())).orderByChild("producto").equalTo(arrayList.get(position).getProducto());
                                findCarrito.addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                                        Item item = objSnapshot.getValue(Item.class);
                                                        Item nvoItem = new Item();
                                                        nvoItem.setIdUsuario(user.getUid());
                                                        nvoItem.setProducto(arrayList.get(position).getProducto());
                                                        nvoItem.setImg(item.getImg());
                                                        nvoItem.setTalla(idTalla);
                                                        nvoItem.setPrecio(item.getPrecio());
                                                        nvoItem.setCantidad(Integer.parseInt(cantidadEditCar.getText().toString()));
                                                        ref.child("Carrito/" + user.getUid() + "/Items").child(item.getProducto()).setValue(nvoItem).addOnSuccessListener(aVoid -> {
                                                        }).addOnFailureListener(e -> {
                                                        });
                                                        CustomToast.mostarToast("Carrito actualizado", 1, true, root, layoutInflater, context);
                                                        dialog.dismiss();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                            } else if (cant == 0) {
                                databaseReference.child("Carrito/" + user.getUid() + "/Items").child(arrayList.get(position).getProducto()).removeValue();
                            } else {
                                Toast.makeText(getContext(), "La cantidad no es valida", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "La cantidad no es valida", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantidadEditCar.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Cantidad invalida", Toast.LENGTH_SHORT).show();
                    cantidadEditCar.setText("1");
                } else {

                    try {
                        int cant = Integer.parseInt(cantidadEditCar.getText().toString().trim());
                        if (cant >= 1) {
                            int sumaCantidad = cant + 1;
                            cantidadEditCar.setText(sumaCantidad + "");
                            float sub = arrayList.get(position).getPrecio() * sumaCantidad;
                            txtSubtotal.setText("$" + sub + "");
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Número invalido", Toast.LENGTH_SHORT).show();
                        cantidadEditCar.setText("1");
                    }

                }
            }
        });
        cantidadEditCar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int cant = 0;
                    try {
                        cant = Integer.parseInt(cantidadEditCar.getText().toString());

                        float sub = arrayList.get(position).getPrecio() * cant;
                        txtSubtotal.setText("$" + sub + "");

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Número invalido", Toast.LENGTH_SHORT).show();
                        cantidadEditCar.setText("1");
                    }
                    return true;
                }
                return false;
            }
        });
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantidadEditCar.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Cantidad invalida", Toast.LENGTH_SHORT).show();
                    cantidadEditCar.setText("1");
                } else {
                    int cant = 0;
                    try {
                        cant = Integer.parseInt(cantidadEditCar.getText().toString());
                        if (cant == 1) {
                            cantidadEditCar.setText("1");
                            float sub = arrayList.get(position).getPrecio() * 1;
                            txtSubtotal.setText("$"+sub + "");
                        } else if (cant > 1) {
                            int restaCantidad = cant - 1;
                            cantidadEditCar.setText(restaCantidad + "");
                            float sub = arrayList.get(position).getPrecio() * restaCantidad;
                            txtSubtotal.setText("$" + sub + "");
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Número invalido", Toast.LENGTH_SHORT).show();
                        cantidadEditCar.setText("1");
                    }

                }
            }
        });

        databaseReference.child("Tallas").orderByChild("idTalla").equalTo(arrayList.get(position).getTalla()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Tallas p = objSnapshot.getValue(Tallas.class);
                    idTalla = tallaDatabase;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinTall.setText(idTalla);
        spinTall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idTalla = customAdapterTalla.getItem(position).getId();
            }
        });
    }

    public void ver(int position) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        final View aboutPop = layoutInflater.inflate(R.layout.dialog_ver_prod_carrito, null);
        TextView txtNombreEditCar = aboutPop.findViewById(R.id.txtVerProdCarVerNombre);
        TextView txtPrecioEditCar = aboutPop.findViewById(R.id.txtVerProdCarVerPrecioD);
        TextView txtDescEditCar = aboutPop.findViewById(R.id.txtVerProdVerCarDescD);
        TextView spinTall = aboutPop.findViewById(R.id.spinVerProdCarVerTallaD);
        TextView cantidadEditCar = aboutPop.findViewById(R.id.editVerProdCarVerCantidadD);
        RatingBar raitingBar = aboutPop.findViewById(R.id.raitingBarVerCarVerProd);
        TextView txtPrecioAntiguo = aboutPop.findViewById(R.id.txtVerProdPrecioCarVerAntiguoD);
        TextView txtDescuento = aboutPop.findViewById(R.id.txtVerProdCarVerDescuentoD);
        Button btnCerrar = aboutPop.findViewById(R.id.btnVerProdCarVerCerrarD);

        ImageView imgEditCar = aboutPop.findViewById(R.id.imgVerCarVerProdD);
        databaseReference.child("Tallas").orderByChild("idTalla").equalTo(arrayList.get(position).getTalla()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Tallas p = objSnapshot.getValue(Tallas.class);
                    spinTall.setText(p.getTallas() + " Medidas: " + p.getMedidas());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        txtPrecioEditCar.setText("$" + arrayList.get(position).getPrecio());
        cantidadEditCar.setText("" + arrayList.get(position).getCantidad());
        //  etPopNombre.setText(arrayList.get(position).getDisenio());
        storageReference.child(arrayList.get(position).getImg()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(imgEditCar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Ha ocurrido un error al leer la imagen", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child("Productos").orderByChild("idProducto").equalTo(arrayList.get(position).getProducto()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        Productos item = objSnapshot.getValue(Productos.class);
                        txtNombreEditCar.setText(item.getNombre());
                        txtDescEditCar.setText(item.getDescripcion());
                        float raitingStar = 0;
                        if (item.getRaiting().isEmpty()) {
                            raitingStar = 0;
                        } else {
                            raitingStar = Float.parseFloat(item.getRaiting());
                        }
                        raitingBar.setRating(raitingStar);
                        txtDescuento.setText(item.getDescuento());
                        Float precioPublico;
                        if (item.getDescuento().equals("") || item.getDescuento().equals("0")) {
                            txtDescuento.setVisibility(View.GONE);
                            txtPrecioAntiguo.setVisibility(View.GONE);
                            txtPrecioEditCar.setText("$" + item.getPrecioVenta());
                            precioPublico = Float.parseFloat(item.getPrecioVenta());
                        } else {
                            txtDescuento.setVisibility(View.VISIBLE);
                            float descuento = Float.parseFloat(item.getDescuento());

                            float precio = Float.parseFloat(item.getPrecioVenta());
                            float descuentoReal = (descuento * precio) / 100;
                            float precioActual = precio - descuentoReal;
                            float redondeo = Math.round(precioActual);
                            txtPrecioAntiguo.setText(item.getPrecioVenta());
                            precioPublico = redondeo;
                            txtPrecioAntiguo.setPaintFlags(txtPrecioAntiguo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            txtPrecioAntiguo.setText("$" + item.getPrecioVenta());
                            txtPrecioEditCar.setText("$" + redondeo);
                            txtDescuento.setText("Descuento del: " + item.getDescuento() + "%");
                        }

                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        dialogBuilder.setView(aboutPop);
        dialog = dialogBuilder.create();
        dialog.show();
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}