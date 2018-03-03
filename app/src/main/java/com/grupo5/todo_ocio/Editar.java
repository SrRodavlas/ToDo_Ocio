package com.grupo5.todo_ocio;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.grupo5.todo_ocio.BD.BBDD_Metodos_helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Editar extends Activity {

    private TextView txt_nombreLugar, txt_bio;
    private ImageView img_Foto;
    private MapView mpv_Localizacion;
    private RatingBar rtnBar;
    private RadioButton rdb_eCine, rdb_eParque, rdb_eRestaurante;
    final BBDD_Metodos_helper helper = new BBDD_Metodos_helper(this);
    private Activity activity;
    private Bundle extras;
    private int posicion;
    private String imagen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_editar);


        // Bundle datosEditar = getIntent().getExtras();
        //final String nombre = datosEditar.getString("nombre");
        //String bio = datosEditar.getString("bio");

        //txt_nombreLugar.setText(nombre);
        //txt_bio.setText(bio);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            }
        }

        txt_nombreLugar = findViewById(R.id.txt_eNombreLugar);
        txt_bio = findViewById(R.id.txt_eBio);
        img_Foto = findViewById(R.id.img_eFoto);
        mpv_Localizacion = findViewById(R.id.mpv_eLocalizacion);
        rtnBar = findViewById(R.id.rtn_eBar);
        rdb_eCine = findViewById(R.id.rdb_eCine);
        rdb_eParque = findViewById(R.id.rdb_eParque);
        rdb_eRestaurante = findViewById(R.id.rdb_eRestaurante);
        activity = this;
        extras = getIntent().getExtras();

        if (extras != null) {
            posicion = extras.getInt("posicion");
            imagen = Lista.lugares.get(posicion).getImagen();
            if (Lista.lugares.get(posicion).getCategoria().equals("Cine")) {
                rdb_eCine.setChecked(true);
            } else if (Lista.lugares.get(posicion).getCategoria().equals("Parque")) {
                rdb_eParque.setChecked(true);
            } else {
                rdb_eRestaurante.setChecked(true);
            }
            txt_nombreLugar.setText(Lista.lugares.get(posicion).getNombre());
            txt_bio.setText(Lista.lugares.get(posicion).getDescripcion());
            rtnBar.setRating(Lista.lugares.get(posicion).getPuntuacion());
            File fichero = new File(imagen);
            if (fichero.exists()) {
                Bitmap bMap = BitmapFactory.decodeFile(fichero.getPath());
                img_Foto.setImageBitmap(bMap);
            } else {
                img_Foto.setImageDrawable(getDrawable(R.drawable.no_disponible));
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                Bitmap bMap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/toDoOcio/foto.jpg");
                img_Foto.setImageBitmap(bMap);
                imagen = MediaStore.Images.Media.insertImage(getContentResolver(), bMap, "miLugar" , "");
                imagen = new File(obtenerDireccionImagen(Uri.parse(imagen))).getAbsolutePath();
            } else if (requestCode == 2 && resultCode == RESULT_OK) {
                try {
                    Uri imagenDireccion = data.getData();
                    File imageFile = new File(obtenerDireccionImagen(imagenDireccion));
                    imagen = imageFile.getAbsolutePath();
                    InputStream imageStream = getContentResolver().openInputStream(imagenDireccion);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img_Foto.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("Error", e.toString());
                }

            }else {
                Toast.makeText(getApplicationContext(), R.string.t_imagenNoEscogida,Toast.LENGTH_LONG).show();
            }
    }

    private String obtenerDireccionImagen(Uri direccionImagen) {
        String resultado;
        Cursor cursor = getContentResolver().query(direccionImagen, null, null, null, null);
        if (cursor == null) {
            resultado = direccionImagen.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            resultado = cursor.getString(idx);
            cursor.close();
        }
        return resultado;
    }
       //Actualizar datos BD
//        btn_Guardar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SQLiteDatabase db = helper.getWritableDatabase();
//
//                // New value for one column
//                ContentValues values = new ContentValues();
//                values.put(Estructura_BBDD.nombre, txt_nombreLugar.getText().toString());
//                values.put(Estructura_BBDD.bio, txt_bio.getText().toString());
//                values.put(Estructura_BBDD.puntuacion, rtnBar.getNumStars());
//
//
//                // Which row to update, based on the title
//                //Esto debería hacerse con el id
//                String selection = Estructura_BBDD.nombre + " LIKE ?";
//                String[] selectionArgs = { txt_nombreLugar.getText().toString() };
//
//                int count = db.update(
//                        Estructura_BBDD.TABLE_NAME,
//                        values,
//                        selection,
//                        selectionArgs);
//
//                Toast.makeText(getApplicationContext(), "Datos actualizados",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });


    public void tomarFoto (View v) {
        File carpetaFotos = new File(Environment.getExternalStorageDirectory(), "toDoOcio");
        carpetaFotos.mkdirs();
        File image = new File(carpetaFotos, "foto.jpg");
        Uri guardarComo = Uri.fromFile(image);
        Intent camara = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        camara.putExtra(MediaStore.EXTRA_OUTPUT, guardarComo);
        startActivityForResult(camara, 1);
    }

    public void galeria (View v) {
        Intent galeria = new Intent(Intent.ACTION_PICK);
        galeria.setType("image/*");
        startActivityForResult(galeria, 2);
    }

    //Actualizar o insertar registro en la base de datos
    public void actualizar (View v){
        if(extras != null) {
            //TODO Actualiza el registro. Instrucciones para SQLite

            Toast.makeText(getApplicationContext(), R.string.t_registroGuerdado,
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(activity, Lista.class);
            startActivity(i);
        } else {
            //TODO Nuevo registro. Instrucciones para SQLite

            Toast.makeText(getApplicationContext(), R.string.t_registroNuevo,
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(activity, Lista.class);
            startActivity(i);
        }
        /*SQLiteDatabase db = helper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Estructura_BBDD.nombre, txt_nombreLugar.getText().toString());
        values.put(Estructura_BBDD.bio, txt_bio.getText().toString());
        values.put(Estructura_BBDD.puntuacion, rtnBar.getNumStars());


        // Which row to update, based on the title
        //Esto debería hacerse con el id, en este caso usa el nombre y actualiza ese registro en la BD
        String selection = Estructura_BBDD.nombre + " LIKE ?";
        String[] selectionArgs = { txt_nombreLugar.getText().toString() };

        int count = db.update(
                Estructura_BBDD.TABLE_NAME,
                values,
                selection,
                selectionArgs);*/
    }


}
