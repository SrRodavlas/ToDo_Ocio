package com.grupo5.todo_ocio;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<MarkerOptions> marcadores;
    private String categoria;
    GoogleMap mvp_mapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        categoria = getIntent().getExtras().getString("categoria");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        marcadores = new ArrayList<>();
        if(getIntent().getExtras().getString("categoria").equals("Todos")){
            añadirMarcadores(true, true, true);
        } else if(getIntent().getExtras().getString("categoria").equals("Cines")){
            añadirMarcadores(true, false, false);
        } else if(getIntent().getExtras().getString("categoria").equals("Parques")){
            añadirMarcadores(false, true, false);
        } else{
            añadirMarcadores(false, false, true);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        //Aquí se añade un marcador en una posición concreta y se configura que la
        //cámara se posicione ahí
        /*LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));*/
        for (int x = 0; x < marcadores.size(); x++){
            googleMap.addMarker(marcadores.get(x));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marcadores.get(0).getPosition()));
    }

    private void añadirMarcadores(boolean cines, boolean parques, boolean restaurantes){
        for (int x = 0; x < Lista.lugares.size(); x++){
            if(cines && Lista.lugares.get(x).getCategoria().equals("Cine")){
                marcadores.add(new MarkerOptions().position(new LatLng(Lista.lugares.get(x).getLongitud(), Lista.lugares.get(x).getLatitud()))
                        .title(Lista.lugares.get(x).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            if(parques && Lista.lugares.get(x).getCategoria().equals("Parque")){
                marcadores.add(new MarkerOptions().position(new LatLng(Lista.lugares.get(x).getLongitud(), Lista.lugares.get(x).getLatitud()))
                        .title(Lista.lugares.get(x).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            if(restaurantes && Lista.lugares.get(x).getCategoria().equals("Restaurante")){
                marcadores.add(new MarkerOptions().position(new LatLng(Lista.lugares.get(x).getLongitud(), Lista.lugares.get(x).getLatitud()))
                        .title(Lista.lugares.get(x).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
    }
}








    //pedir permisos de localizacion
    /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    mvp_mapa = (MapView)findViewById(R.id.mvp_mapa);

        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mvp_mapa.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        } */

