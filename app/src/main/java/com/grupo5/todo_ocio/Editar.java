package com.grupo5.todo_ocio;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.grupo5.todo_ocio.list.Lugar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Editar extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    private GoogleMap mapa;
    private LocationManager locationManager;
    private Location loc;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private boolean isGPS = false;
    private boolean isNetwork = false;
    private boolean canGetLocation = true;
    private TextView txt_nombreLugar, txt_bio;
    private ImageView img_Foto;
    private RatingBar rtnBar;
    private RadioButton rdb_eCine, rdb_eParque, rdb_eRestaurante;
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

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (!isGPS && !isNetwork) {
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }
            getLocation();
        }

        SupportMapFragment mpv_vLocalizacion = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mpv_eLocalizacion);
        mpv_vLocalizacion.getMapAsync(this);

        if (!extras.getBoolean("nuevo")) {
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
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        if(!extras.getBoolean("nuevo")) {
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
        if(rdb_eCine.isChecked() || rdb_eParque.isChecked() || rdb_eRestaurante.isChecked()){
            if(!extras.getBoolean("nuevo")) {
                Lista.lugares.get(posicion).setNombre("" + txt_nombreLugar.getText());
                Lista.lugares.get(posicion).setDescripcion(("" + txt_bio.getText()));
                if(rdb_eCine.isChecked()){
                    Lista.lugares.get(posicion).setCategoria(getString(R.string.spin_filtradoCine));
                } else if(rdb_eParque.isChecked()){
                    Lista.lugares.get(posicion).setCategoria(getString(R.string.spin_filtradoParque));
                } else {
                    Lista.lugares.get(posicion).setCategoria(getString(R.string.spin_filtradoRestaurante));
                }
                Lista.lugares.get(posicion).setPuntuacion(rtnBar.getRating());
                Lista.lugares.get(posicion).setLongitud(loc.getLongitude());
                Lista.lugares.get(posicion).setLatitud(loc.getLatitude());
                Lista.lugares.get(posicion).setImagen(imagen);
                Log.i("Categoria", Lista.lugares.get(posicion).getCategoria());
                Lista.sqlite.actualizar(Lista.lugares.get(posicion));
                Toast.makeText(getApplicationContext(), R.string.t_registroGuerdado,
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, Lista.class);
                startActivity(i);
            } else {
                Lugar nuevoLugar = new Lugar();
                nuevoLugar.setNombre("" + txt_nombreLugar.getText());
                nuevoLugar.setDescripcion("" + txt_bio.getText());
                if(rdb_eCine.isChecked()){
                    nuevoLugar.setCategoria(getString(R.string.spin_filtradoCine));
                } else if(rdb_eParque.isChecked()){
                    nuevoLugar.setCategoria(getString(R.string.spin_filtradoParque));
                } else {
                    nuevoLugar.setCategoria(getString(R.string.spin_filtradoRestaurante));
                }
                nuevoLugar.setPuntuacion(rtnBar.getRating());
                nuevoLugar.setLongitud(loc.getLongitude());
                nuevoLugar.setLatitud(loc.getLatitude());
                nuevoLugar.setImagen(imagen);
                Log.i("Categoria", nuevoLugar.getCategoria());
                Lista.sqlite.insertar(nuevoLugar);
                Toast.makeText(getApplicationContext(), R.string.t_registroNuevo,
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, Lista.class);
                startActivity(i);
            }
        } else{
            dialogoCategoria(getString(R.string.diag_categoriaMensajeGuardar));
        }
    }

    private void dialogoCategoria(String parteMensaje){
        String mensaje = getString(R.string.diag_categoriaMensage) + " " + parteMensaje;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.diag_categoriaTitulo);
        alertDialog.setMessage(mensaje);
        alertDialog.setPositiveButton(R.string.dbtn_Aceptar, null);
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel(getString(R.string.diag_permisosMensaje),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.diag_gpsTitulo);
        alertDialog.setMessage(R.string.diag_gpsMensaje);
        alertDialog.setPositiveButton(R.string.dbtn_Aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(R.string.dbtn_Cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton( R.string.dbtn_Aceptar, okListener)
                .setNegativeButton(R.string.dbtn_Cancelar, null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        if(mapa != null){
            mapa.clear();
            if(rdb_eCine.isChecked()){
                mapa.addMarker(new MarkerOptions().position(new LatLng(loc.getLongitude(), loc.getLatitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            if(rdb_eParque.isChecked()){
                mapa.addMarker(new MarkerOptions().position(new LatLng(loc.getLongitude(), loc.getLatitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            if(rdb_eRestaurante.isChecked()){
                mapa.addMarker(new MarkerOptions().position(new LatLng(loc.getLongitude(), loc.getLatitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLongitude(), loc.getLatitude()), 16.0f));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void conseguirLocalicacion(View v){
        if(rdb_eCine.isChecked() || rdb_eParque.isChecked() || rdb_eRestaurante.isChecked()){
            getLocation();
        } else {
            dialogoCategoria(getString(R.string.diag_categoriaMensajeLocalizacion));
        }
    }
}
