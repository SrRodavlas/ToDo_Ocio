package com.grupo5.todo_ocio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class Ver extends AppCompatActivity implements OnMapReadyCallback {

    private Button btn_Borrar, btn_Editar;  //boton para borrar en BD
    private TextView txt_vNombreLugar, txt_vBio;
    private ImageView img_vFoto;
    private RatingBar rtn_vBar;
    private int posicion;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        btn_Borrar = (Button) findViewById(R.id.btn_vBorrar);
        txt_vNombreLugar = (TextView) findViewById(R.id.lbl_vNombre);
        txt_vBio = (EditText) findViewById(R.id.lbl_vBio);
        rtn_vBar = (RatingBar) findViewById(R.id.rtn_vBar);
        img_vFoto = (ImageView) findViewById(R.id.img_vFoto);
        posicion = getIntent().getExtras().getInt("posicion");

        txt_vNombreLugar.setText(txt_vNombreLugar.getText() + " "
                + Lista.lugares.get(posicion).getNombre());
        txt_vBio.setText(Lista.lugares.get(posicion).getDescripcion());
        rtn_vBar.setRating(Lista.lugares.get(posicion).getPuntuacion());

        SupportMapFragment mpv_vLocalizacion = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mpv_vLocalizacion);
        mpv_vLocalizacion.getMapAsync(this);

        File fichero = new File(Lista.lugares.get(posicion).getImagen());
        if(fichero.exists()) {
            Bitmap bMap = BitmapFactory.decodeFile(fichero.getPath());
            img_vFoto.setImageBitmap(bMap);
        }
        else {
            img_vFoto.setImageDrawable(getDrawable(R.drawable.no_disponible));
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        if(Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoCine))){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Lista.lugares.get(posicion).getLongitud(), Lista.lugares.get(posicion).getLatitud()))
                    .title(Lista.lugares.get(posicion).getNombre())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        if(Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoParque))){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Lista.lugares.get(posicion).getLongitud(), Lista.lugares.get(posicion).getLatitud()))
                    .title(Lista.lugares.get(posicion).getNombre())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        if(Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoRestaurante))){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Lista.lugares.get(posicion).getLongitud(), Lista.lugares.get(posicion).getLatitud()))
                    .title(Lista.lugares.get(posicion).getNombre())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lista.lugares.get(posicion).getLongitud(), Lista.lugares.get(posicion).getLatitud()), 16.0f));
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
               Lista.sqlite.borrar(Lista.lugares.get(posicion));
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
        i.putExtra("posicion", posicion);
        i.putExtra("nuevo", false);
        startActivity(i);
    }
}


