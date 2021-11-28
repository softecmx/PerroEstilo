package com.programacion.perroestilocliente.ui.cliente.direcciones.agregarDireccion;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.programacion.perroestilocliente.modelo.Direcciones;
import com.programacion.perroestilocliente.modelo.GeoPoint;
import com.programacion.perroestilocliente.ui.cliente.direcciones.misDirecciones.ListAdapter;

import java.util.UUID;

public class AgregarDireccionFragment extends Fragment implements OnMapReadyCallback {

    private AgregarDireccionViewModel mViewModel;
    GoogleMap gMap;

    MapView mapView;
    Marker marcador;
    float txtLongitud;
    float txtLatitud;
    FusedLocationProviderClient client;


    AutoCompleteTextView txtEstado;
    AutoCompleteTextView txtMunicipio;
    TextView txtLocalidad;
    TextView txtCodigoPostal;
    TextView txtCalleExterior;
    TextView txtCalleInterior;
    TextView txtReferencias;
    Button btnAgregar;
    Button btnLimpiar;
    Button btnCancelar;
    ArrayAdapter<CharSequence> municipioAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;
    private LocationManager locationManager;
    public static int REQUEST_PERMISSION = 1;
    /*
    Modificar la ruta

                try {
                    latitud = Float.parseFloat(reporteSeleccionado.getLatitud());
                    longitud = Float.parseFloat(reporteSeleccionado.getLongitud());

                } catch (NumberFormatException e) {

                }
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title("Ubicación del Animal"));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud)));
                gMap.moveCamera(CameraUpdateFactory.zoomTo(15));

     */
    public static AgregarDireccionFragment newInstance() {
        return new AgregarDireccionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_agregar_direccion, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mapView = (MapView) root.findViewById(R.id.mapViewAddDireccion);

        txtEstado = root.findViewById(R.id.spinAddDirEstado);
        txtMunicipio = root.findViewById(R.id.spinAddDirMuncipio);
        txtLocalidad = root.findViewById(R.id.editAddDirLocalidad);
        txtCodigoPostal = root.findViewById(R.id.editAddDirCodPostal);
        txtCalleExterior = root.findViewById(R.id.editAddDirCalleExt);
        txtCalleInterior = root.findViewById(R.id.editAddDirCalleInt);
        txtReferencias = root.findViewById(R.id.editAddDirReferencias);
        btnAgregar = root.findViewById(R.id.btnAddDireccion);
        btnLimpiar = root.findViewById(R.id.btnAddDirLimpiar);
        btnCancelar = root.findViewById(R.id.btnAddDirecCancelar);

        ArrayAdapter<CharSequence> spnEstado, spnMunicipio;
        spnEstado = ArrayAdapter.createFromResource(getContext(), R.array.estados, R.layout.spinner_item_model);
        spnMunicipio = ArrayAdapter.createFromResource(getContext(), R.array.opc0, R.layout.spinner_item_model);
        txtEstado.setAdapter(spnEstado);
        txtMunicipio.setAdapter(spnMunicipio);

        txtEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc0, R.layout.spinner_item_model);
                    case 1:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc1, R.layout.spinner_item_model);
                        break;
                    case 2:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc2, R.layout.spinner_item_model);
                        break;
                    case 3:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc3, R.layout.spinner_item_model);
                        break;
                    case 4:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc4, R.layout.spinner_item_model);
                        break;
                    case 5:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc5, R.layout.spinner_item_model);
                        break;
                    case 6:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc6, R.layout.spinner_item_model);
                        break;
                    case 7:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc7, R.layout.spinner_item_model);
                        break;
                    case 8:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc8, R.layout.spinner_item_model);
                        break;
                    case 9:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc9, R.layout.spinner_item_model);
                        break;
                    case 10:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc10, R.layout.spinner_item_model);
                        break;
                    case 11:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc11, R.layout.spinner_item_model);
                        break;
                    case 12:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc12, R.layout.spinner_item_model);
                        break;
                    case 13:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc13, R.layout.spinner_item_model);
                        break;
                    case 14:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc14, R.layout.spinner_item_model);
                        break;
                    case 15:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc15, R.layout.spinner_item_model);
                        break;
                    case 16:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc16, R.layout.spinner_item_model);
                        break;
                    case 17:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc17, R.layout.spinner_item_model);
                        break;
                    case 18:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc18, R.layout.spinner_item_model);
                        break;
                    case 19:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc19, R.layout.spinner_item_model);
                        break;
                    case 20:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc20, R.layout.spinner_item_model);
                        break;
                    case 21:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc21, R.layout.spinner_item_model);
                        break;
                    case 22:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc22, R.layout.spinner_item_model);
                        break;
                    case 23:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc23, R.layout.spinner_item_model);
                        break;
                    case 24:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc24, R.layout.spinner_item_model);
                        break;
                    case 25:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc25, R.layout.spinner_item_model);
                        break;
                    case 26:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc26, R.layout.spinner_item_model);
                        break;
                    case 27:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc27, R.layout.spinner_item_model);
                        break;
                    case 28:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc28, R.layout.spinner_item_model);
                        break;
                    case 29:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc29, R.layout.spinner_item_model);
                        break;
                    case 30:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc30, R.layout.spinner_item_model);
                        break;
                    case 31:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc31, R.layout.spinner_item_model);
                        break;
                    case 32:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc32, R.layout.spinner_item_model);
                        break;

                    default:
                        municipioAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opc0, R.layout.spinner_item_model);
                        break;
                }
                txtMunicipio.setAdapter(municipioAdapter);
            }
        });


        int permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }


        initGoogleMap(savedInstanceState);
        client = LocationServices.getFusedLocationProviderClient(getActivity());



        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String estado=txtEstado.getText().toString();
                String municipio2=txtMunicipio.getText().toString();
                String localidad=txtLocalidad.getText().toString();
                String codigoPostal=txtCodigoPostal.getText().toString();
                String calleExt=txtCalleExterior.getText().toString();
                String calleInt=txtCalleInterior.getText().toString();
                String ref=txtReferencias.getText().toString();
                //getCurrentLocation();
                Direcciones direccion=new Direcciones();
                direccion.setIdDireccion(UUID.randomUUID().toString());
                direccion.setEntidadFederativa(estado);
                direccion.setMunicipio(municipio2);
                direccion.setLocalidad(localidad);
                direccion.setCodigoPostal(codigoPostal);
                direccion.setCalleYNumeroExterno(calleExt);
                direccion.setCalleYNumeroInterno(calleInt);
                direccion.setReferencia(ref);
                GeoPoint coordenadas=new GeoPoint();
                coordenadas.setLatitud(String.valueOf(txtLatitud));
                coordenadas.setLongitud(String.valueOf(txtLongitud));
                direccion.setCoordenadas(coordenadas);
                direccion.setIdUser(user.getUid());


                databaseReference.child("Direcciones").child(direccion.getIdDireccion()).setValue(direccion).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // limpiar();
                        CustomToast.mostarToast("Direccion registrada!",1,true,root,getLayoutInflater(),getContext());

                    }
                });
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgregarDireccionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && (grantResults.length > 0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getActivity(), "Permiso no definido", Toast.LENGTH_LONG).show();
        }
    }


    private void initGoogleMap(Bundle savedInstanceState) {

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }

        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle("MapViewBundleKey");
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle("MapViewBundleKey", mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void activaGPS() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setMessage("Es necesario activar el GPS, ¿Desea hacerlo ahora?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        dialogo.setNegativeButton("No", null);
        dialogo.show();
    }


    private void getUbicacion() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                txtLatitud = Float.parseFloat(String.valueOf(location.getLatitude()));
                txtLongitud = Float.parseFloat(String.valueOf(location.getLongitude()));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.addMarker(new MarkerOptions().position(new LatLng(19.116620, -99.525949)).title("Marcador"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(19.116620, -99.525949)));
        gMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //gMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marcador2"));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            activaGPS();
        } else {
            getUbicacion();

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                txtLatitud=Float.parseFloat(String.valueOf(location.getLatitude()));
                txtLongitud=Float.parseFloat(String.valueOf(location.getLongitude()));
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(new LatLng(txtLatitud, txtLongitud)).title("Mi ubicación"));
                gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(txtLatitud, txtLongitud)));
                gMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
        gMap.setMyLocationEnabled(true);
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {

            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        txtLatitud = Float.parseFloat(String.valueOf(location.getLatitude()));
                        txtLongitud = Float.parseFloat(String.valueOf(location.getLongitude()));
                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                txtLatitud = Float.parseFloat(String.valueOf(location.getLatitude()));
                                txtLongitud = Float.parseFloat(String.valueOf(location.getLongitude()));
                            }
                        };

                        client.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


}