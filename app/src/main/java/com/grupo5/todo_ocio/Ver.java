package com.grupo5.todo_ocio;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.android.gms.maps.MapView;

public class Ver extends Activity{

    Button btn_Borrar;  //boton para borrar en BD
    EditText txt_vNombreLugar, txt_vBio;
    ImageView img_vFoto;
    MapView mpv_vLocalizacion;
    RatingBar rtn_vBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
    }

    //Funcionalidad del botón editar
    @SuppressLint("WrongViewCast")
    public void ejeccutar_editar(View view){
        Intent i = new Intent(this, Nuevo.class);

        i.putExtra("nombre", txt_vNombreLugar.getText());
        i.putExtra("bio", txt_vBio.getText());

        startActivity(i);

        btn_Borrar = (Button)findViewById(R.id.btn_Borrar);
        txt_vNombreLugar = (EditText)findViewById(R.id.lbl_vNombre);
        txt_vBio = (EditText)findViewById(R.id.lbl_vBio);
        rtn_vBar = (RatingBar)findViewById(R.id.rtn_vBar);
        mpv_vLocalizacion = (MapView)findViewById(R.id.mpv_vLocalizacion);
    }

}
