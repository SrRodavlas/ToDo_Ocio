package com.grupo5.todo_ocio;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.MapView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

public class Nuevo extends Activity {

    Button btn_Guardar; //Boton para insertar en BD
    TextView txt_nombreLugar, txt_bio;
    ImageButton btn_Foto;
    MapView mpv_Localizacion;
    static RatingBar rtnBar;
    static TextView txtRating;
    RadioGroup id_radioGroup;
    //Para acceder a la BD se crea una instancia de la subclase SQLiteOpenHelper
    final BBDD_Metodos_helper helper = new BBDD_Metodos_helper(this);
    Button bb;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);


        btn_Guardar = (Button) findViewById(R.id.btn_nGuardar);
        txt_nombreLugar = (TextView) findViewById(R.id.txt_nNombreLugar);
        txt_bio = (EditText) findViewById(R.id.txt_nBio);
        btn_Foto = (ImageButton) findViewById(R.id.img_nFoto);
        mpv_Localizacion = (MapView) findViewById(R.id.mvp_mapa);
        rtnBar = (RatingBar) findViewById(R.id.rtnBar);
        id_radioGroup = (RadioGroup) findViewById(R.id.id_radioGroup);
        txtRating = (TextView) findViewById(R.id.rtbText);

        btn_Guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertar(v);
            }
        });

//        btn_Foto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {diaux();}
//        });



    }

    //Para acceder a la BD se crea una instancia de la subclase SQLiteOpenHelper
    //    final BBDD_Metodos_helper helper = new BBDD_Metodos_helper(this);

    //Botón para insertar los datos en la BD

    //   btn_Guardar.setOnClickListener(new View.OnClickListener(){

    //       @Override
    //        public void onClick(View v) {
    // Gets the data repository in write mode
    //       SQLiteDatabase db = helper.getWritableDatabase();

                /* Se indica la clase donde se ha definido la estructura de la BD + el nombre del campo +
                   donde está la info nueva + más los métodos necesarios
                   faltan los datos de mapa, imagen... que hay que averiguar como recogerlos
                 */
//                ContentValues values = new ContentValues();
//                values.put(Estructura_BBDD.nombre,txt_nombreLugar.getText().toString());
//                values.put(Estructura_BBDD.bio, txt_bio.getText().toString());
//                values.put(Estructura_BBDD.puntuacion, rtnBar.getNumStars());
//                values.put(Estructura_BBDD.tag, id_radioGroup.getCheckedRadioButtonId());
    //values.put(Estructura_BBDD.imagen, img_Foto.get);
    //values.put(Estructura_BBDD.latitud, mpv_Localizacion.get);

    // Insert the new row, returning the primary key value of the new row
    //Aquí se guarda el valor del id de la nueva fila creada
    //       long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

    //        Toast.makeText(getApplicationContext(), "Registro guardado, id: " +
    //                newRowId, Toast.LENGTH_LONG).show();


    //});

    // }


    public void insertar(View v) {

        // Gets the data repository in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

                /* Se indica la clase donde se ha definido la estructura de la BD + el nombre del campo +
                   donde está la info nueva + más los métodos necesarios
                   faltan los datos de mapa, imagen... que hay que averiguar como recogerlos
                 */
        ContentValues values = new ContentValues();
        values.put(Estructura_BBDD.nombre, txt_nombreLugar.getText().toString());
        values.put(Estructura_BBDD.bio, txt_bio.getText().toString());
        //values.put(Estructura_BBDD.puntuacion, rtnBar.getNumStars());
        //values.put(Estructura_BBDD.tag, id_radioGroup.getCheckedRadioButtonId());
        //values.put(Estructura_BBDD.imagen, img_Foto.get);
        //values.put(Estructura_BBDD.latitud, mpv_Localizacion.get);

        // Insert the new row, returning the primary key value of the new row
        //Aquí se guarda el valor del id de la nueva fila creada
        long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

        Toast.makeText(getApplicationContext(), "Registro guardado, id: " +
                newRowId, Toast.LENGTH_LONG).show();
    }

    //Funcionalidad RatingBar
    public static void rtnBarFunctionality() {
        rtnBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRating.setText(String.valueOf(rating));
            }

        });
    }


//    public static void diaux(){
//        PhotoDialogFragment dialog = new PhotoDialogFragment();
//        dialog.setMenuVisibility(true);
//    }
}

