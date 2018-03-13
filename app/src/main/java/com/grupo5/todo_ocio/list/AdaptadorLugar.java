package com.grupo5.todo_ocio.list;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.grupo5.todo_ocio.R;

import java.io.File;
import java.util.ArrayList;

public class AdaptadorLugar extends BaseAdapter {
    private Activity activity;
    private ArrayList<Lugar> items;

    public AdaptadorLugar(Activity activity, ArrayList<Lugar> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void limpiar() {
        items.clear();
    }

    public void añadirTodo(ArrayList<Lugar> lugar) {
        items.addAll(lugar);
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_categoria, parent, false);

        Lugar dir = items.get(position);

        TextView title = itemView.findViewById(R.id.txt_TituloItem);
        title.setText(dir.getNombre());

        RatingBar puntuacion = itemView.findViewById(R.id.rtn_puntuacion);
        puntuacion.setRating(dir.getPuntuacion());

        ImageView imagen = itemView.findViewById(R.id.img_ImagenItem);
        File fichero = new File(dir.getImagen());
        if(fichero.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bMap = BitmapFactory.decodeFile(fichero.getPath(), options);
            imagen.setImageBitmap(bMap);
        }
        else {
            imagen.setImageDrawable(activity.getDrawable(R.drawable.no_disponible));
        }
        return itemView;
    }
}
