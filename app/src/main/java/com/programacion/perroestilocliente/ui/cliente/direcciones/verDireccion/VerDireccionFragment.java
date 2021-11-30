package com.programacion.perroestilocliente.ui.cliente.direcciones.verDireccion;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.programacion.perroestilocliente.R;
import com.programacion.perroestilocliente.modelo.Direcciones;
import com.programacion.perroestilocliente.modelo.GeoPoint;

public class VerDireccionFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap gMap;
    MapView mapView;
    Marker marcador;
    float txtLongitud;
    float txtLatitud;
    Button btnRegresar;
    Button btnEditar;
    Button btnEliminar;
    TextView txtEstado;
    TextView txtMunicipio;
    TextView txtLocalidad;
    TextView txtCodPostal;
    TextView txtCalleExt;
    TextView txtCalleInt;
    TextView txtRef;
    FusedLocationProviderClient client;
    private VerDireccionViewModel mViewModel;
    View root;
    String id;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseUser user;
    private LocationManager locationManager;
    public static int REQUEST_PERMISSION = 1;
    Direcciones direcciones;

    public static VerDireccionFragment newInstance() {
        return new VerDireccionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ver_direccion, container, false);
        Bundle bound = getArguments();
        id = bound.getString("idDireccion");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mapView = (MapView) root.findViewById(R.id.mapViewVerDireccion);
        txtEstado = root.findViewById(R.id.txtVerDirEstado);
        txtMunicipio = root.findViewById(R.id.txtVerDirMunicipio);
        txtLocalidad = root.findViewById(R.id.txtVerDirLocalidad);
        txtCodPostal = root.findViewById(R.id.txtVerDirCodPostal);
        txtCalleExt = root.findViewById(R.id.txtVerDirCalleExt);
        txtCalleInt = root.findViewById(R.id.txtVerDirCalleInt);
        txtRef = root.findViewById(R.id.txtVerDirReferencias);
        btnEditar=root.findViewById(R.id.btnVerDirEditar);
        btnEliminar=root.findViewById(R.id.btnVerDirEliminar);
        btnRegresar=root.findViewById(R.id.btnVerDirCerrar);
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

        Query queryDireccion = databaseReference.child("Direcciones").orderByChild("idDireccion").equalTo(id);
        queryDireccion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                        direcciones = objSnapshot.getValue(Direcciones.class);
                        txtEstado.setText(direcciones.getEntidadFederativa());
                        txtMunicipio.setText(direcciones.getMunicipio());
                        txtLocalidad.setText(direcciones.getLocalidad());
                        txtCodPostal.setText(direcciones.getCodigoPostal());
                        txtCalleExt.setText(direcciones.getCalleYNumeroExterno());
                        txtCalleInt.setText(direcciones.getCalleYNumeroInterno());
                        txtRef.setText(direcciones.getReferencia());
                        GeoPoint point = direcciones.getCoordenadas();
                        try {
                            txtLatitud = Float.parseFloat(point.getLatitud());
                            txtLongitud = Float.parseFloat(point.getLongitud());
                            gMap.clear();
                            gMap.addMarker(new MarkerOptions().position(new LatLng(txtLatitud, txtLongitud)).title("Mi dirección"));
                            gMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(txtLatitud, txtLongitud)));
                            gMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                        } catch (NumberFormatException e) {

                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerDireccionViewModel.class);
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
                txtLatitud = Float.parseFloat(String.valueOf(location.getLatitude()));
                txtLongitud = Float.parseFloat(String.valueOf(location.getLongitude()));
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