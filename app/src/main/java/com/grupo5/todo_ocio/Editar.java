package com.grupo5.todo_ocio;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grupo5.todo_ocio.list.Lugar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Editar extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = Editar.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000;
    private static int FATEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;
    private GoogleMap mapa;
    private TextView txt_nombreLugar, txt_bio;
    private ImageView img_Foto;
    private RatingBar rtnBar;
    private RadioButton rdb_eCine, rdb_eParque, rdb_eRestaurante;
    private Activity activity;
    private Bundle extras;
    private int posicion;
    private double latitud;
    private double longitud;
    private boolean estaUbicado;
    private String imagen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_editar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            }
        }

        txt_nombreLugar = findViewById(R.id.txt_eNombreLugar);
        txt_bio = findViewById(R.id.txt_eBio);
        img_Foto = findViewById(R.id.img_eFoto);
        rtnBar = findViewById(R.id.rtn_eBar);
        rdb_eCine = findViewById(R.id.rdb_eCine);
        rdb_eParque = findViewById(R.id.rdb_eParque);
        rdb_eRestaurante = findViewById(R.id.rdb_eRestaurante);
        activity = this;
        extras = getIntent().getExtras();
        estaUbicado = false;

        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }

        SupportMapFragment mpv_vLocalizacion = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mpv_eLocalizacion);
        mpv_vLocalizacion.getMapAsync(this);

        if (!extras.getBoolean("nuevo")) {
            estaUbicado = true;
            posicion = extras.getInt("posicion");
            imagen = Lista.lugares.get(posicion).getImagen();
            if (Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoCine))) {
                rdb_eCine.setChecked(true);
            } else if (Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoParque))) {
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
        } else {
            imagen = "vacio";
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        if(!extras.getBoolean("nuevo")) {
            if(Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoCine))){
                googleMap.addMarker(new MarkerOptions().position(new LatLng(Lista.lugares.get(posicion).getLatitud(), Lista.lugares.get(posicion).getLongitud()))
                        .title(Lista.lugares.get(posicion).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            if(Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoParque))){
                googleMap.addMarker(new MarkerOptions().position(new LatLng(Lista.lugares.get(posicion).getLatitud(), Lista.lugares.get(posicion).getLongitud()))
                        .title(Lista.lugares.get(posicion).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            if(Lista.lugares.get(posicion).getCategoria().equals(getString(R.string.spin_filtradoRestaurante))){
                googleMap.addMarker(new MarkerOptions().position(new LatLng(Lista.lugares.get(posicion).getLatitud(), Lista.lugares.get(posicion).getLongitud()))
                        .title(Lista.lugares.get(posicion).getNombre())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lista.lugares.get(posicion).getLatitud(), Lista.lugares.get(posicion).getLongitud()), 16.0f));
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
                    Log.i("Imagen", imagen);
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
    public void actualizarGuardarRegistro(View v){
        if(rdb_eCine.isChecked() || rdb_eParque.isChecked() || rdb_eRestaurante.isChecked()) {
            if(estaUbicado) {
                String nombre = "" + txt_nombreLugar.getText();
                if(!nombre.trim().equals("")){
                    if(!extras.getBoolean("nuevo")) {
                        Lista.lugares.get(posicion).setNombre(nombre);
                        Lista.lugares.get(posicion).setDescripcion(("" + txt_bio.getText()));
                        if(rdb_eCine.isChecked()){
                            Lista.lugares.get(posicion).setCategoria(getString(R.string.spin_filtradoCine));
                        } else if(rdb_eParque.isChecked()){
                            Lista.lugares.get(posicion).setCategoria(getString(R.string.spin_filtradoParque));
                        } else {
                            Lista.lugares.get(posicion).setCategoria(getString(R.string.spin_filtradoRestaurante));
                        }
                        Lista.lugares.get(posicion).setPuntuacion(rtnBar.getRating());
                        Lista.lugares.get(posicion).setLongitud(longitud);
                        Lista.lugares.get(posicion).setLatitud(latitud);
                        Lista.lugares.get(posicion).setImagen(imagen);
                        Log.i("Categoria", Lista.lugares.get(posicion).getCategoria());
                        Lista.sqlite.actualizar(Lista.lugares.get(posicion));
                        Toast.makeText(getApplicationContext(), R.string.t_registroGuerdado,
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity, Lista.class);
                        startActivity(i);
                    } else {
                        Lugar nuevoLugar = new Lugar();
                        nuevoLugar.setNombre(nombre);
                        nuevoLugar.setDescripcion("" + txt_bio.getText());
                        if(rdb_eCine.isChecked()){
                            nuevoLugar.setCategoria(getString(R.string.spin_filtradoCine));
                        } else if(rdb_eParque.isChecked()){
                            nuevoLugar.setCategoria(getString(R.string.spin_filtradoParque));
                        } else {
                            nuevoLugar.setCategoria(getString(R.string.spin_filtradoRestaurante));
                        }
                        nuevoLugar.setPuntuacion(rtnBar.getRating());
                        nuevoLugar.setLongitud(longitud);
                        nuevoLugar.setLatitud(latitud);
                        nuevoLugar.setImagen(imagen);
                        Log.i("Categoria", nuevoLugar.getCategoria());
                        Lista.sqlite.insertar(nuevoLugar);
                        Toast.makeText(getApplicationContext(), R.string.t_registroNuevo,
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity, Lista.class);
                        startActivity(i);
                    }
                } else {
                    dialogoCategoria(getString(R.string.diag_nombreTitulo),
                            getString(R.string.diag_nombreMensaje));
                }
            } else {
                dialogoCategoria(getString(R.string.diag_localizadorTitulo),
                        getString(R.string.diag_localizadorMensaje));
            }
        } else{
            dialogoCategoria(getString(R.string.diag_categoriaTitulo),
                    getString(R.string.diag_categoriaMensage) + " "
                    + getString(R.string.diag_categoriaMensajeGuardar));
        }
    }

    private void dialogoCategoria(String titulo, String mensaje){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensaje);
        alertDialog.setPositiveButton(R.string.dbtn_GuardarAceptar, null);
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            displayLocation();
            Log.d("onStart ", "GoogleApiClient se ha creado");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        if (mGoogleApiClient.isConnected() && mRequestLocationUpdates) {
            startLocationUpdates();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    private void displayLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }else{
            //Do Your Stuff

        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.d("Ultima Ubicación ", "encontrada");
        if (mLastLocation != null) {
            latitud = mLastLocation.getLatitude();
            longitud = mLastLocation.getLongitude();
        } else {
            latitud = 0.0;
            longitud = 0.0;
        }
    }


    private void togglePeriodLocationUpdates() {
        if (!mRequestLocationUpdates) {
            mRequestLocationUpdates = true;
            startLocationUpdates();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        Log.d("GoogleApiClient ", " se ha creado");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        Log.d("Solicitud de ubicacion", " es creado");
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "Este dispositivo no es compatible.");
                finish();
            }
            return false;
        }
        return true;
    }


    protected void startLocationUpdates() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }else{

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        Log.d("La ubicación ", "está actualizada");
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        if (mRequestLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Conexion fallida: " + connectionResult.getErrorCode());
    }

    private void updateUI() {
        if(mapa != null){
            mapa.clear();
            if(rdb_eCine.isChecked()){
                mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            if(rdb_eParque.isChecked()){
                mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            if(rdb_eRestaurante.isChecked()){
                mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 16.0f));
        }
    }

    public void conseguirLocalicacion(View v){
        if(rdb_eCine.isChecked() || rdb_eParque.isChecked() || rdb_eRestaurante.isChecked()){
            //getLocation();
            ActivityCompat.requestPermissions(Editar.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

            togglePeriodLocationUpdates();
            updateUI();
            if(!estaUbicado) {
                estaUbicado = true;
            }
        } else {
            dialogoCategoria(getString(R.string.diag_categoriaTitulo),
                    getString(R.string.diag_categoriaMensage) + " "
                            + getString(R.string.diag_categoriaMensajeLocalizacion));
        }
    }
}
