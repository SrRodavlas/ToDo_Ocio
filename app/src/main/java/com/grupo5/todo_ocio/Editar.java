package com.grupo5.todo_ocio;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.google.android.gms.maps.MapView;

public class Editar extends Activity {

    Button btn_Guardar; //Boton para insertar en BD
    EditText txt_nombreLugar, txt_bio;
    ImageView img_Foto;
    MapView mpv_Localizacion;
    RatingBar rtnBar;
    RadioGroup id_radioGroup;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);


        Bundle datosEditar = getIntent().getExtras();
        String nombre = datosEditar.getString("nombre");
        String bio = datosEditar.getString("bio");

        txt_nombreLugar.setText(nombre);
        txt_bio.setText(bio);


        btn_Guardar = (Button) findViewById(R.id.btn_eGuardar);
        txt_nombreLugar = (EditText) findViewById(R.id.txt_eNombreLugar);
        txt_bio = (EditText) findViewById(R.id.txt_eBio);
        img_Foto = (ImageView) findViewById(R.id.img_eFoto);
        mpv_Localizacion = (MapView) findViewById(R.id.mpv_eLocalizacion);
        rtnBar = (RatingBar) findViewById(R.id.rtn_eBar);
        id_radioGroup = (RadioGroup) findViewById(R.id.id_eRadioGroup);
    }
}
