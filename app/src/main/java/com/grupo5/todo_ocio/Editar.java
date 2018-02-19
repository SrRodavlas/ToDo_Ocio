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
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

public class Editar extends Activity {

    Button btn_Guardar; //Boton para insertar en BD
    EditText txt_nombreLugar, txt_bio;
    ImageView img_Foto;
    MapView mpv_Localizacion;
    RatingBar rtnBar;
    RadioGroup id_radioGroup;
    final BBDD_Metodos_helper helper = new BBDD_Metodos_helper(this);


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Nuevo.rtnBarFunctionality();


        Bundle datosEditar = getIntent().getExtras();
        final String nombre = datosEditar.getString("nombre");
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

       //Actualizar datos BD
        btn_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getWritableDatabase();

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.nombre, txt_nombreLugar.getText().toString());
                values.put(Estructura_BBDD.bio, txt_bio.getText().toString());
                values.put(Estructura_BBDD.puntuacion, rtnBar.getNumStars());


                // Which row to update, based on the title
                //Esto debería hacerse con el id
                String selection = Estructura_BBDD.nombre + " LIKE ?";
                String[] selectionArgs = { txt_nombreLugar.getText().toString() };

                int count = db.update(
                        Estructura_BBDD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(), "Datos actualizados",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
