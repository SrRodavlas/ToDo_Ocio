package com.grupo5.todo_ocio;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.grupo5.todo_ocio.BD.BBDD_Metodos_helper;

import java.io.File;

public class Ver extends Activity{

    private Button btn_Borrar, btn_Editar;  //boton para borrar en BD
    private TextView txt_vNombreLugar, txt_vBio;
    private ImageView img_vFoto;
    private MapView mpv_vLocalizacion;
    private RatingBar rtn_vBar;
    private int posicion;
    private Activity activity = this;

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
        btn_Editar = (Button)findViewById(R.id.action_edit); //botón del menú barra
        posicion = getIntent().getExtras().getInt("posicion");

        txt_vNombreLugar.setText(txt_vNombreLugar.getText() + " "
                + Lista.lugares.get(posicion).getNombre());
        txt_vBio.setText(Lista.lugares.get(posicion).getDescripcion());
        rtn_vBar.setRating(Lista.lugares.get(posicion).getPuntuacion());
        File fichero = new File(Lista.lugares.get(posicion).getImagen());
        if(fichero.exists()) {
            Bitmap bMap = BitmapFactory.decodeFile(fichero.getPath());
            img_vFoto.setImageBitmap(bMap);
        }
        else {
            img_vFoto.setImageDrawable(getDrawable(R.drawable.no_disponible));
        }




        /*btn_Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {delete(v);}
        });*/





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
   public void borrar (View v){
       AlertDialog.Builder borrarLugar = new AlertDialog.Builder(this);
       borrarLugar.setTitle(R.string.diag_tituloBorrado);
       borrarLugar.setMessage(R.string.diag_mensajeBorrado);
       borrarLugar.setCancelable(false);
       borrarLugar.setPositiveButton(R.string.btn_confirmar, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               //TODO Instruciones para borrar en la base de datos el registro
               /*//Para poder modificar datos
               SQLiteDatabase db = helper.getWritableDatabase();
               // Define 'where' part of query.
               String selection = Estructura_BBDD.nombre + " LIKE ?";
               // Specify arguments in placeholder order.
               String[] selectionArgs = {txt_vNombreLugar.getText().toString()};
               // Issue SQL statement.
               db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs);*/
               //Lista.lugares.remove(posicion);
               Toast.makeText(getApplicationContext(), R.string.t_registroBorrado,
                       Toast.LENGTH_SHORT).show();
               Intent i = new Intent(activity, Lista.class);
               startActivity(i);
           }
       });

       borrarLugar.setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               //No ocurre nada
           }
       });
       borrarLugar.show();
   }


    //Funcionalidad del botón editar
    public void editar(View view){

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
        i.putExtra("posicion", posicion);
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


