package com.programacion.perroestilocliente.ui.administrador.productos.consultarProductos;

import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.CustomToast;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Disenios;
import com.programacion.perroestilocliente.modelo.Productos;
import com.programacion.perroestilocliente.ui.administrador.productos.ElementListView;
import com.programacion.perroestilocliente.ui.administrador.productos.ListAdapterSimple;

import java.util.ArrayList;

public class ConsultarProductosFragment extends Fragment {

    private ConsultarProductosViewModel mViewModel;

    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String img="";

    EditText etId;
    View root;
    ImageButton ibtnconsProdbsc;
    ListView lista;

    ListAdapterSimple customAdapter;
    ElementListView productoSelected;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    ArrayList<ElementListView> arrayList;

    public static ConsultarProductosFragment newInstance() {
        return new ConsultarProductosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_consultar_productos, container, false);
        lista = root.findViewById(R.id.lvConsProdLista);
        etId = root.findViewById(R.id.etConsProdid);
        ibtnconsProdbsc = root.findViewById(R.id.ibtnconsProdbsc);

        iniciaFirebase();
        cargaDatos();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                productoSelected = (ElementListView) arg0.getItemAtPosition(position);
                myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                myClip = ClipData.newPlainText("text", productoSelected.getIdProducto());
                myClipboard.setPrimaryClip(myClip);
                ClipData clip = ClipData.newPlainText("simple text", productoSelected.getIdProducto());
                CustomToast.mostarToast("ID copiado!",1,false,root,getLayoutInflater(),getContext());
            }
        });
        ibtnconsProdbsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etId.getText().toString().equals("")){
                    CustomToast.mostarToast("Ingrese un dato",2,false,root,getLayoutInflater(),getContext());
                }else{
                    ArrayList<ElementListView> arrayBusca = new ArrayList<>();
                    for (int i = 0;i<arrayList.size();i++){
                        if (arrayList.get(i).getNombre().toUpperCase().equals(etId.getText().toString().toUpperCase())){
                            arrayBusca.add(arrayList.get(i));
                        }
                    }
                    customAdapter = new ListAdapterSimple(getActivity(), arrayBusca,getContext());
                    lista.setAdapter(customAdapter);
                }
            }
        });
        return root;
    }

    public void cargaDatos(){
        databaseReference.child("Productos").orderByChild("estadoLogico").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Productos p = objSnapshot.getValue(Productos.class);
                    arrayList.add(new ElementListView(p.getIdProducto(),p.getNombre(), p.getIdCategoria(), p.getDisenio(), p.getTalla(), p.getPrecioVenta(), p.getCosto(), p.getDescuento(), p.getFechaCreacion(), p.getRaiting(), p.getEstatusStock(), p.getStock(), p.getEstadoLogico(),p.getImgFoto(),p.getDescripcion()));
                    customAdapter = new ListAdapterSimple(getActivity(), arrayList,getContext());
                    lista.setAdapter(customAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void iniciaFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Disenios");
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConsultarProductosViewModel.class);
        // TODO: Use the ViewModel
    }

}