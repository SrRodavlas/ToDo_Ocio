package com.grupo5.todo_ocio;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Ver extends Activity{

    Button btn_Borrar;  //boton para borrar en BD
    TextView txt_vNombreLugar, txt_vBio;
    ImageView img_vFoto;
    MapView mpv_vLocalizacion;
    RatingBar rtn_vBar;
    Button edit;

    //Para acceder a la BD se crea una instancia de la subclase SQLiteOpenHelper
    final BBDD_Metodos_helper helper = new BBDD_Metodos_helper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        btn_Borrar = (Button) findViewById(R.id.btn_vBorrar);
        txt_vNombreLugar = (TextView) findViewById(R.id.lbl_vNombre);
        txt_vBio = (EditText) findViewById(R.id.lbl_vBio);
        rtn_vBar = (RatingBar) findViewById(R.id.rtn_vBar);
        mpv_vLocalizacion = (MapView) findViewById(R.id.mpv_vLocalizacion);
        img_vFoto = (ImageView) findViewById(R.id.img_vFoto);
        edit = (Button)findViewById(R.id.action_edit); //botón del menú barra


        //Eliminar registros base de datos al pulsar botón borrar
//        btn_Borrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Para poder modificar datos
//                SQLiteDatabase db = helper.getWritableDatabase();
//                // Define 'where' part of query.
//                String selection = Estructura_BBDD.nombre + " LIKE ?";
//                // Specify arguments in placeholder order.
//                String[] selectionArgs = {txt_vNombreLugar.getText().toString()};
//                // Issue SQL statement.
//                db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs);
//
//                Toast.makeText(getApplicationContext(), "Datos eliminados",
//                        Toast.LENGTH_SHORT).show();
//
//                txt_vNombreLugar.setText("");
//                txt_vBio.setText("");
//            }
//        });

//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }



    //Eliminar registros base de datos al pulsar botón borrar
   public void delete (View v){

       //Para poder modificar datos
       SQLiteDatabase db = helper.getWritableDatabase();
       // Define 'where' part of query.
       String selection = Estructura_BBDD.nombre + " LIKE ?";
       // Specify arguments in placeholder order.
       String[] selectionArgs = {txt_vNombreLugar.getText().toString()};
       // Issue SQL statement.
       db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs);

       Toast.makeText(getApplicationContext(), "Datos eliminados",
               Toast.LENGTH_SHORT).show();

       txt_vNombreLugar.setText("");
       txt_vBio.setText("");

   }




















    //Funcionalidad del botón editar

    public void ejectuar_editar(View view){

        Intent i = new Intent(this, Editar.class);

        //i.putExtra("nombre", txt_vNombreLugar.getText());
        //i.putExtra("bio", txt_vBio.getText());
        //i.putExtra("imagen",img_vFoto.getDrawingCache());

        //obtener la imagen?--------------------------------------------------------------
//        img_vFoto.buildDrawingCache();
//        Bitmap bitmap = img_vFoto.getDrawingCache();
//        OutputStream fileOutStream = null;
//        Uri uri;
//        try {
//            File file = new File(Environment.getExternalStorageDirectory()
//                    + File.separator + "imagenesguardadas" + File.separator);
//            file.mkdirs();
//            File directorioImagenes = new File(file, "mi_imagen.jpg");
//            uri = Uri.fromFile(directorioImagenes);
//            fileOutStream = new FileOutputStream(directorioImagenes);
//        } catch (Exception e) {
//            Log.e("ERROR!", e.getMessage());
//        }
//        try {
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
//            fileOutStream.flush();
//            fileOutStream.close();
//        } catch (Exception e) {
//            Log.e("ERROR!", e.getMessage());
//        }
        //---------------------------------------------------------------------------------------------

        startActivity(i);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (super.onCreateOptionsMenu(menu)) return true;
        else return false;
    }
}


