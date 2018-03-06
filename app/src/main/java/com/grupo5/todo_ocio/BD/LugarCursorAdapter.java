package com.grupo5.todo_ocio.BD;

/**
 * Created by Luisao on 06/03/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.grupo5.todo_ocio.R;

import java.io.File;

public class LugarCursorAdapter extends CursorAdapter {

    public LugarCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.activity_lista, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {


        TextView nombreTxt = (TextView) view.findViewById(R.id.lbl_vNombre);
        TextView biografiaTxt = (TextView) view.findViewById(R.id.lbl_vBio);
       // RatingBar rate = (RatingBar) view.findViewById(R.id.ratBarSel);
       // ImageView imagen = (ImageView) view.findViewById(R.id.imgLugar);


        String name = cursor.getString(cursor.getColumnIndex(EsquemaLugar.EntradaLugar.NOMBRE));
        String biog = cursor.getString(cursor.getColumnIndex(EsquemaLugar.EntradaLugar.BIO));
        Float nota = Float.parseFloat(cursor.getString(cursor.getColumnIndex(EsquemaLugar.EntradaLugar.PUNTUACION)));
        String imag = cursor.getString(cursor.getColumnIndex(EsquemaLugar.EntradaLugar.IMAGEN));


        nombreTxt.setText(name);
        biografiaTxt.setText(biog);
       // rate.setRating(nota);
        Bitmap icon=null;
        if(imag.equals(""))
        {
            icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.sinimagen);
        }
        else if(imag.startsWith("/"))
        {
            File imgFile = new File(imag);
            if(imgFile.exists()) {
                icon=BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
            else {
                icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.sinimagen);
            }
        }
        else
        {
           icon=StringBitmap.StringToBitMap(imag);
        }
       imag.setImageBitmap(icon);

    }

}