package com.grupo5.todo_ocio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<MarkerOptions> marcadores;
    private String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        categoria = getIntent().getExtras().getString("categoria");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_mapa);
        mapFragment.getMapAsync(this);

        marcadores = new ArrayList<>();
        if(getIntent().getExtras().getString("categoria").equals(getString(R.string.spin_filtradoTodo))){
            añadirMarcadores(true, true, true);
        } else if(getIntent().getExtras().getString("categoria").equals(getString(R.string.spin_filtradoCine))){
            añadirMarcadores(true, false, false);
        } else if(getIntent().getExtras().getString("categoria").equals(getString(R.string.spin_filtradoParque))){
            añadirMarcadores(false, true, false);
        } else{
            añadirMarcadores(false, false, true);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        for (int x = 0; x < marcadores.size(); x++){
            googleMap.addMarker(marcadores.get(x));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marcadores.get(0).getPosition()));
    }

    private void añadirMarcadores(boolean cines, boolean parques, boolean restaurantes){
        for (int x = 0; x < Lista.lugares.size(); x++){
            if(cines && Lista.lugares.get(x).getCategoria().equals(getString(R.string.spin_filtradoCine))){
                marcadores.add(new MarkerOptions().position(new LatLng(Lista.lugares.get(x).getLongitud(), Lista.lugares.get(x).getLatitud()))
                        .title(Lista.lugares.get(x).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            if(parques && Lista.lugares.get(x).getCategoria().equals(getString(R.string.spin_filtradoParque))){
                marcadores.add(new MarkerOptions().position(new LatLng(Lista.lugares.get(x).getLongitud(), Lista.lugares.get(x).getLatitud()))
                        .title(Lista.lugares.get(x).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            if(restaurantes && Lista.lugares.get(x).getCategoria().equals(getString(R.string.spin_filtradoRestaurante))){
                marcadores.add(new MarkerOptions().position(new LatLng(Lista.lugares.get(x).getLongitud(), Lista.lugares.get(x).getLatitud()))
                        .title(Lista.lugares.get(x).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
    }
}