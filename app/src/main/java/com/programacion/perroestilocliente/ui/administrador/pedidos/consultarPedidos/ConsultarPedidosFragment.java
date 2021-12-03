package com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos;

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
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.MisPedidosViewModel;
import com.programacion.perroestilocliente.ui.cliente.pedidos.misPedidos.ReciclerViewAdapterPedidos;

import java.util.ArrayList;

public class ConsultarPedidosFragment extends Fragment implements AdapterView.OnItemClickListener {

    ImageButton btnBuscar;
    private ConsultarPedidosViewModel mViewModel;
    View root;
    RecyclerView listView;
    String totalOrden;
    ArrayList<ElementListViewConsultarPedidos> arrayListOrdenes = new ArrayList<>();
    private ListAdapterConsultarPedidos adapterOrdenes;

    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

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


        return root;
    }

    public void listarDatos() {


        /*
        databaseReference.child("OrdenesCliente/").orderByChild("estatusOrden").equalTo("Pago pendiente").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    arrayListOrdenes.clear();
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        OrdenesCliente orden = objSnapshot.getValue(OrdenesCliente.class);
                        Toast.makeText(getContext(), "S se encontraron", Toast.LENGTH_SHORT).show();
                    /*Toast.makeText(getContext(), "Recuperando datos...", Toast.LENGTH_LONG).show();
                    OrdenesCliente orden = objSnapshot.getValue(OrdenesCliente.class);
                    arrayListOrdenes.add(new ElementListViewConsultarPedidos(orden.getInOrden(), orden.getEstatusOrden(), "$ " + orden.getTotal()));
                    adapterOrdenes = new ListAdapterConsultarPedidos(getActivity(), arrayListOrdenes);
                    listView.setAdapter(adapterOrdenes);
                    }
                }else Toast.makeText(getContext(), "No hay datos", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

*/
    }

    public void iniciaFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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