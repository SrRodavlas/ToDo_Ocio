package com.grupo5.todo_ocio;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.MapView;

import java.util.Map;

public class Mapa extends Activity{

    MapView mvp_mapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
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
}
