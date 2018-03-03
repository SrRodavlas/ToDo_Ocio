package com.grupo5.todo_ocio;


import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;


public class PhotoDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.Description)
                .setPositiveButton(R.string.Camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.Gallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    public interface PhotoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    PhotoDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (PhotoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PhotoDialogListener");
        }
    }






























     /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DESDE_CAMARA) {

            if (data != null) {

                if (data.hasExtra("data")) {
                    ib = (ImageButton) getActivity().findViewById(R.id.img_nFoto);
                    ib.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                }

            } else {

                ib = (ImageButton) getActivity().findViewById(R.id.img_nFoto);
                ib.setImageBitmap(BitmapFactory.decodeFile(name));

                new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null;

                    {
                        msc = new MediaScannerConnection(getActivity().getApplicationContext(), this);
                        msc.connect();
                    }

                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };
            }

        } else if (requestCode == DESDE_GALERIA) {
            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = getActivity().getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                ib = (ImageButton) getActivity().findViewById(R.id.img_nFoto);
                ib.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }*/
}







































//CODIGO SIN MODIFICAR, ASI FUNCIONA
//public class PhotoDialogFragment extends DialogFragment {
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage(R.string.Description)
//                .setPositiveButton(R.string.Camera, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                })
//                .setNegativeButton(R.string.Gallery, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//        return builder.create();
//    }
//}

