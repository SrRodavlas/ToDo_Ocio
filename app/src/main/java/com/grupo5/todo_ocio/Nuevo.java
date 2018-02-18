package com.grupo5.todo_ocio;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;


import com.google.android.gms.maps.MapView;

public class Nuevo extends Activity {

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
        setContentView(R.layout.activity_nuevo);

        //para ventana editar habrá que crearla al final creo----------------------------------------------------------

        Bundle datosEditar = getIntent().getExtras();
        String nombre = datosEditar.getString("nombre");
        String bio = datosEditar.getString("bio");

        txt_nombreLugar.setText(nombre);
        txt_bio.setText(bio);

        //-----------------------------------------------------------------------------

        btn_Guardar = (Button)findViewById(R.id.btn_Borrar);
        txt_nombreLugar = (EditText)findViewById(R.id.txt_nombreLugar);
        txt_bio = (EditText)findViewById(R.id.txt_bio);
        img_Foto = (ImageView)findViewById(R.id.img_Foto);
        mpv_Localizacion = (MapView)findViewById(R.id.mvp_mapa);
        rtnBar = (RatingBar)findViewById(R.id.rtnBar);
        id_radioGroup = (RadioGroup)findViewById(R.id.id_radioGroup);

        //Para acceder a la BD se crea una instancia de la subclase SQLiteOpenHelper
        final BBDD_Metodos_helper helper = new BBDD_Metodos_helper(this);

        //Botón para insertar los datos en la BD
        btn_Guardar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.nombre,txt_nombreLugar.getText().toString());
                values.put(Estructura_BBDD.bio, txt_bio.getText().toString());
                values.put(Estructura_BBDD.puntuacion, rtnBar.getNumStars());
                values.put(Estructura_BBDD.tag, id_radioGroup.getCheckedRadioButtonId());
                //values.put(Estructura_BBDD.imagen, img_Foto.get);
                //values.put(Estructura_BBDD.latitud, mpv_Localizacion.get);

// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

            }
        });

    }
}
