package com.programacion.perroestilocliente.ui.cliente.comprar.comprarAhora;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.ListView;
import android.widget.TextView;

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
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.bd.Item;
import com.programacion.perroestilocliente.modelo.Clientes;
import com.programacion.perroestilocliente.modelo.DetOrdenProductos;
import com.programacion.perroestilocliente.modelo.Direcciones;
import com.programacion.perroestilocliente.modelo.OrdenesCliente;
import com.programacion.perroestilocliente.ui.cliente.comprar.ElementListView;
import com.programacion.perroestilocliente.ui.cliente.comprar.ListAdapterSimple;
import com.programacion.perroestilocliente.ui.cliente.comprar.confirmarCompra.ConfirmarCompraFragment;
import com.programacion.perroestilocliente.ui.cliente.mainCliente.ListAdapterCarrito;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

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
    ArrayList<Item> arrayListItems;
    AutoCompleteTextView spinComAhEscogerDirecc;
    private ListAdapterSimple customAdapterDric;
    ArrayList<ElementListView> arrayList;
    String dir = "";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String idCliente="";
    private ComprarAhoraViewModel mViewModel;
    View root;

    public static ComprarAhoraFragment newInstance() {
        return new ComprarAhoraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_comprar_ahora, container, false);
        spinComAhEscogerDirecc=root.findViewById(R.id.spinComAhEscogerDirecc);
        btnComprarAhoraCancelar = root.findViewById(R.id.btnComprarAhoraCancelar);
        btnComprarAhoraConfirmar = root.findViewById(R.id.btnComprarAhoraConfirmar);
        txtNombre=root.findViewById(R.id.txtCompAhoNomCont);
        txtApellidos=root.findViewById(R.id.txtCompAhoApellidoPCont);
        txtTelefono=root.findViewById(R.id.txtCompAhoTelefonoCont);
        txtEstado=root.findViewById(R.id.txtCompAhEstado);
        txtMunicipio=root.findViewById(R.id.txtCompAhMunicipio);
        txtLocalidad=root.findViewById(R.id.txtCompAhLocalidad);
        txtCodigoPostal=root.findViewById(R.id.txtCompAhCodPostal);
        txtCalleExterior=root.findViewById(R.id.txtCompAhCalleExt);
        txtCalleInterior=root.findViewById(R.id.txtCompAhCalleInt);
        txtReferencias=root.findViewById(R.id.txtCompAhReferencias);

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Productos");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query queryCliente = databaseReference.child("Usuarios/Clientes").orderByChild("email").equalTo(user.getEmail());
        queryCliente.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                                        Clientes usuario = objSnapshot.getValue(Clientes.class);
                                        idCliente=usuario.getIdUsuario();
                                        txtNombre.setText(usuario.getNombreCliente());
                                        txtApellidos.setText(usuario.getApellidoPaterno());
                                        txtTelefono.setText(usuario.getTelefono());
                                    }
                                }
                            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("Direcciones").orderByChild("idUser").equalTo(user.getUid()).
                addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                arrayList = new ArrayList<>();
                spinComAhEscogerDirecc.setAdapter(null);
                for (DataSnapshot objSnapshot: snapshot.getChildren()){
                    Direcciones p = objSnapshot.getValue(Direcciones.class);
                    arrayList.add(new ElementListView(p.getIdDireccion(),p.getEntidadFederativa(),
                            p.getMunicipio(),p.getLocalidad(),p.getCalleYNumeroExterno(),p.getCalleYNumeroInterno(),p.getReferencia(),
                            p.getCodigoPostal()));
                    customAdapterDric = new ListAdapterSimple(getActivity(), arrayList);
                    spinComAhEscogerDirecc.setAdapter(customAdapterDric);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });

        spinComAhEscogerDirecc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //dir=customAdapterDric.getItem(position).getIdDireccion();
                txtEstado.setText(customAdapterDric.getItem(position).getEntidadFederativa());
                txtMunicipio.setText(customAdapterDric.getItem(position).getMunicipio());
                txtLocalidad.setText(customAdapterDric.getItem(position).getLocalidad());
                txtCodigoPostal.setText(customAdapterDric.getItem(position).getCodigoPostal());
                txtCalleExterior.setText(customAdapterDric.getItem(position).getCalleYNumeroExterno());
                txtCalleInterior.setText(customAdapterDric.getItem(position).getCalleYNumeroInterno());
                txtReferencias.setText(customAdapterDric.getItem(position).getReferencia());
            }
        });
        // btnPopCerrar = (Button) aboutPop.findViewById(R.id.btnCerrarDialog);
        TextView txtTotal = root.findViewById(R.id.txtComprarAhoraTotalPagar);
        ListView reciclerViewMiCarritoProductos = root.findViewById(R.id.lstViewComprarAhoraCarrito);
         arrayListItems = new ArrayList<>();
        total = 0;
        databaseReference.child("Carrito/" + user.getUid() + "/Items").addListenerForSingleValueEvent(new ValueEventListener() {
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
                realizarPedido();
            }
        });
        btnComprarAhoraCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Carrito").child(user.getUid()).removeValue();
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

    public void realizarPedido(){

        String fechaPedido=new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        String idOrden= (UUID.randomUUID().toString()).subSequence(0,10).toString().toUpperCase();
        String estatus="Pago pendiente";
        String noSerie1=UUID.randomUUID().toString();
        String noSerie2="";
        char[] toChar=noSerie1.toCharArray();
        for (int i=0; i<toChar.length;i++){
            if(toChar[i]=='-'){

            }else{
                noSerie2+=toChar[i];
            }
        }
        float total=0;
        for (int i=0;i<arrayListItems.size();i++){
            total=total+(arrayListItems.get(i).getCantidad()*arrayListItems.get(i).getPrecio());
        }

        noSerie1=noSerie2.substring(0,12).toUpperCase();
        String noConfirm="0";
        OrdenesCliente ordenCliente=new OrdenesCliente();
        ordenCliente.setInOrden(idOrden);
        ordenCliente.setIdCliente(idCliente);
        ordenCliente.setEstatusOrden(estatus);
        ordenCliente.setNoSerie(noSerie1);
        ordenCliente.setFechaOrden(fechaPedido);
        ordenCliente.setNombreContacto(txtNombre.getText().toString());
        ordenCliente.setApPContacto(txtApellidos.getText().toString());
        ordenCliente.setApMContacto("");
        ordenCliente.setTelefonoContacto(txtTelefono.getText().toString());
        ordenCliente.setFechaEntrega("");
        Direcciones direccionEnvio=new Direcciones();
        direccionEnvio.setEntidadFederativa(txtEstado.getText().toString());
        direccionEnvio.setMunicipio(txtMunicipio.getText().toString());
        direccionEnvio.setLocalidad(txtLocalidad.getText().toString());
        direccionEnvio.setCodigoPostal(txtCodigoPostal.getText().toString());
        direccionEnvio.setCalleYNumeroExterno(txtCalleExterior.getText().toString());
        direccionEnvio.setCalleYNumeroInterno(txtCalleInterior.getText().toString());
        direccionEnvio.setReferencia(txtReferencias.getText().toString());
        ordenCliente.setDireccionEnvio(direccionEnvio);
        ordenCliente.setTotal(total);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("OrdenesCliente/"+idCliente).child(ordenCliente.getInOrden()).setValue(ordenCliente);

        for (int i=0; i<arrayListItems.size();i++){
            DetOrdenProductos detOrdenProd=new DetOrdenProductos();
            detOrdenProd.setIdDetOrdenProducto(UUID.randomUUID().toString().toUpperCase());
            detOrdenProd.setIdOrdenCliente(ordenCliente.getInOrden().toUpperCase());
            detOrdenProd.setIdProducto(arrayListItems.get(i).getProducto());
            detOrdenProd.setImgFoto(arrayListItems.get(i).getImg());
            detOrdenProd.setCantidad(arrayListItems.get(i).getCantidad());
            detOrdenProd.setPrecioUnitario(arrayListItems.get(i).getPrecio());
            detOrdenProd.setSubtotal((arrayListItems.get(i).getCantidad()*arrayListItems.get(i).getPrecio())+"");
            detOrdenProd.setMedidas(arrayListItems.get(i).getTalla());
            detOrdenProd.setCalificacion("0");
            ref.child("DetalleOrdenesCliente/"+ordenCliente.getInOrden()).child(detOrdenProd.getIdDetOrdenProducto()).setValue(detOrdenProd);
        }

        ref.child("Carrito").child(user.getUid()).removeValue();
        ConfirmarCompraFragment newFragment1 = new ConfirmarCompraFragment();
        Bundle args = new Bundle();
        args.putString("noOrden",idOrden);
        args.putString("fechaOrden",fechaPedido);
        args.putFloat("total",total);
        newFragment1.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_cliente, newFragment1);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}