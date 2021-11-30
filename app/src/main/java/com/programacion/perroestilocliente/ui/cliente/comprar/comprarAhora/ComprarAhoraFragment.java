package com.programacion.perroestilocliente.ui.cliente.comprar.comprarAhora;

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
import com.programacion.perroestilocliente.ui.cliente.comprar.confirmarCompra.ConfirmarCompraFragment;
import com.programacion.perroestilocliente.ui.cliente.mainCliente.ListAdapterCarrito;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ComprarAhoraFragment extends Fragment {
    float total;
    Button btnComprarAhoraConfirmar;
    Button btnComprarAhoraCancelar;
    TextView txtNombre;
    TextView txtApellidos;
    TextView txtTelefono;
    TextView txtEstado;
    TextView txtMunicipio;
    TextView txtLocalidad;
    TextView txtCodigoPostal;
    TextView txtCalleExterior;
    TextView txtCalleInterior;
    TextView txtReferencias;


    private ComprarAhoraViewModel mViewModel;
    View root;

    public static ComprarAhoraFragment newInstance() {
        return new ComprarAhoraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_comprar_ahora, container, false);

        btnComprarAhoraCancelar = root.findViewById(R.id.btnComprarAhoraCancelar);
        btnComprarAhoraConfirmar = root.findViewById(R.id.btnComprarAhoraConfirmar);

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnCerrarDialog);
        TextView txtTotal = root.findViewById(R.id.txtComprarAhoraTotalPagar);
        ListView reciclerViewMiCarritoProductos = root.findViewById(R.id.lstViewComprarAhoraCarrito);
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
                            total = total + (item.getPrecio() * item.getCantidad());
                            txtTotal.setText("$" + total);
                        } catch (Exception e) {

                        }

                    }
                } else {
                    txtTotal.setText("$0.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnComprarAhoraConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmarCompraFragment newFragment1 = new ConfirmarCompraFragment();
                Bundle args = new Bundle();
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_cliente, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnComprarAhoraCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(ComprarAhoraFragment.this);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ComprarAhoraViewModel.class);
        // TODO: Use the ViewModel
    }

}