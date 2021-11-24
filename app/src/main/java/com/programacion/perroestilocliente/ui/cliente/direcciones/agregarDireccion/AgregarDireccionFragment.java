package com.programacion.perroestilocliente.ui.cliente.direcciones.agregarDireccion;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.programacion.perroestilocliente.R;

public class AgregarDireccionFragment extends Fragment implements OnMapReadyCallback {

    private AgregarDireccionViewModel mViewModel;
    GoogleMap gMap;

    MapView mapView;
    Marker marcador;

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
        View root=inflater.inflate(R.layout.fragment_agregar_direccion, container, false);

        mapView = (MapView) root.findViewById(R.id.mapView);
        initGoogleMap(savedInstanceState);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgregarDireccionViewModel.class);
        // TODO: Use the ViewModel
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
        gMap.setMyLocationEnabled(true);
    }
}