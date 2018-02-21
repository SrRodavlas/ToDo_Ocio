package com.grupo5.todo_ocio;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

public class PhotoDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.Description)
                .setPositiveButton(R.string.Camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // SI PULSAS EL BOTON QUE PONE POSITIVO
                    }
                })
                .setNegativeButton(R.string.Gallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // SI PULSAS EL BOTON QUE PONE NEGATIVO
                    }
                });
        return builder.create();
    }
}


//    public void obtenerClic(View v) {
//        RadioButton radbtnCamara = (RadioButton) findViewById(R.id.radbtnCamara);
//        RadioButton radbtnGaleria = (RadioButton) findViewById(R.id.radbtnGaleria);
//
//        Intent intent = null;
//        int code = 0;
//        if (radbtnCamara.isChecked()) {
//            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            code = DESDE_CAMARA;
//        }
//        if (radbtnGaleria.isChecked()) {
//            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//            code = DESDE_GALERIA;
//        }
//
//        startActivityForResult(intent, code);
//    }

    // Función que se ejecuta cuando concluye el intent en el que se solicita la imagen
    // ya sea desde la cámara como desde la galería
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        Bitmap imagen = null;
//
//        if (requestCode == DESDE_CAMARA) {
//            imagen = (Bitmap) data.getParcelableExtra("data");
//        }
//
//        if (requestCode == DESDE_GALERIA) {
//            Uri rutaImagen = data.getData();
//            try {
//                imagen = BitmapFactory.decodeStream(new BufferedInputStream(getContentResolver().openInputStream(rutaImagen)));
//            } catch (FileNotFoundException e) { }
//        }
//
//        ImageView iv = (ImageView) findViewById(R.id.imgView);
//        iv.setImageBitmap(imagen);
//    }

