package com.programacion.perroestilocliente.ui.cliente.carrito.miCarrito;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.ui.cliente.comprar.comprarAhora.ComprarAhoraFragment;
import com.programacion.perroestilocliente.ui.cliente.mainCliente.ListAdapterCarrito;
import com.programacion.perroestilocliente.ui.cliente.mainCliente.NavClienteActivity;
import com.programacion.perroestilocliente.ui.cliente.productos.verProductoTienda.VerProductoTiendaFragment;
import com.programacion.perroestilocliente.ui.cliente.tienda.TiendaFragment;

import java.util.ArrayList;

public class MiCarritoFragment extends Fragment {

    private MiCarritoViewModel mViewModel;
    View root;
    float total;
    Button btnContinuarComprando;
    Button btnComprarAhora;

    public static MiCarritoFragment newInstance() {
        return new MiCarritoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mi_carrito, container, false);
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnCerrarDialog);
        TextView txtTotal = root.findViewById(R.id.txtFCarritoTotal);
        TextView txtProductos = root.findViewById(R.id.txtFCarritoAunSinComprar);
        btnComprarAhora=root.findViewById(R.id.btnFMiCarritoComprarAhora);
        btnContinuarComprando=root.findViewById(R.id.btnFMiCarritoContinuar);
        ListView reciclerViewMiCarritoProductos = root.findViewById(R.id.lstViewFragMiCarritoProductos);
        ArrayList<Item> arrayListItems = new ArrayList<>();
        total = 0;
        databaseReference.child("Carrito/" + user.getUid() + "/Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListItems.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        //Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                        try {
                            Item item = objSnapshot.getValue(Item.class);
                            arrayListItems.add(item);
                            ListAdapterCarrito adapterProductos = new ListAdapterCarrito(getActivity(), arrayListItems);
                            reciclerViewMiCarritoProductos.setAdapter(adapterProductos);
                            System.out.println(item.getProducto() + "  " + item.getCantidad());
                            total = total + (item.getPrecio() * item.getCantidad());
                            txtTotal.setText("$" + total);
                            txtProductos.setVisibility(View.GONE);
                        }catch (Exception e){

                        }

                    }
                } else {
                    txtTotal.setText("$0.0");
                    txtProductos.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnComprarAhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ComprarAhoraFragment newFragment1 = new ComprarAhoraFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnContinuarComprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TiendaFragment newFragment1 = new TiendaFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiCarritoViewModel.class);
        // TODO: Use the ViewModel
    }

}