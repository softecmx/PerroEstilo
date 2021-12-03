package com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
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
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.ui.administrador.inicio.ElementListViewInicioAdmin;
import com.programacion.perroestilocliente.ui.administrador.inventario.ElementListViewInventario;
import com.programacion.perroestilocliente.ui.administrador.pedidos.verDetallePedido.VerPedidoFragment;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosViewModel;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.ReciclerViewAdapterPedidos;

import java.util.ArrayList;
import java.util.List;

public class ConsultarPedidosFragment extends Fragment implements AdapterView.OnItemClickListener {

    ImageButton btnBuscar;
    private ConsultarPedidosViewModel mViewModel;
    View root;
    ListView listView;
    String totalOrden;
    ArrayList<ElementListViewConsultarPedidos> arrayListOrdenes = new ArrayList<>();
    private ListAdapterConsultarPedidos adapterOrdenes;

    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static ConsultarPedidosFragment newInstance() {
        return new ConsultarPedidosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_consultar_pedidos, container, false);
        listView = root.findViewById(R.id.listPedidosProcesando);
        btnBuscar = root.findViewById(R.id.ibtnBuscarPedidoAdmin);

        iniciaFirebase();
        registerForContextMenu(listView);
        listarDatos();
        verdetalles();

        return root;
    }

    public void listarDatos() {
        arrayListOrdenes.clear();
        databaseReference.child("OrdenesCliente/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ElementListViewConsultarPedidos> arrayList = new ArrayList<>();

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    //Log.i("ids clientes ", objSnapshot.getKey());

                    databaseReference.child("OrdenesCliente/" + objSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot objSnapshot2 : snapshot.getChildren()) {
                                Log.i("Construyendoruta ", "OrdenesCliente/" + objSnapshot.getKey() + "/" + objSnapshot2.getKey());
                                databaseReference.child("OrdenesCliente/" + objSnapshot.getKey() + "/" + objSnapshot2.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {

                                            String estatus = snapshot.child("estatusOrden").getValue().toString();

                                            if (estatus.equals("Preparando pedido")) {
                                                String id = snapshot.child("inOrden").getValue().toString();
                                                String total = snapshot.child("total").getValue().toString();
                                                String idusuario = snapshot.child("idCliente").getValue().toString();

                                                arrayListOrdenes.add(new ElementListViewConsultarPedidos(id, estatus, "$ " + total,idusuario));
                                                adapterOrdenes = new ListAdapterConsultarPedidos(getActivity(), arrayListOrdenes);
                                                listView.setAdapter(adapterOrdenes);
                                            }

                                        } else
                                            Log.i("NO hay objeto", snapshot + "");//si no se encuentran pedidos
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void verdetalles() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ElementListViewConsultarPedidos vistaElement= adapterOrdenes.getItem(position);

                VerPedidoFragment newFragment1 = new VerPedidoFragment();
                Bundle args = new Bundle();
                args.putString("idOrden", vistaElement.getOrdenPedido());
                args.putString("status", vistaElement.getStatusPedido());
                args.putString("total", vistaElement.getTotalPedido());
                args.putString("idusuario", vistaElement.getIdusuario());
                newFragment1.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newFragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarPedidosViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}