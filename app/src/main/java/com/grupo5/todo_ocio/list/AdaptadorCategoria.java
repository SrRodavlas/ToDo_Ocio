package com.grupo5.todo_ocio.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupo5.todo_ocio.R;

import java.util.ArrayList;

public class AdaptadorCategoria extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Categoria> items;

    public AdaptadorCategoria(Activity activity, ArrayList<Categoria> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Categoria> categoria) {
        for (int i = 0; i < categoria.size(); i++) {
            items.add(categoria.get(i));
        }
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

        //View v = convertView;

        /*if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_categoria, null);
        }*/
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.item_categoria, parent, false);

        Categoria dir = items.get(position);

        TextView title = (TextView) itemView.findViewById(R.id.txt_TituloItem);
        title.setText(dir.getTitle());

        TextView description = (TextView) itemView.findViewById(R.id.txt_PuntuacionItem);
        description.setText(dir.getDescripcion());

        ImageView imagen = (ImageView) itemView.findViewById(R.id.img_ImagenItem);
        imagen.setImageDrawable(dir.getImagen());

        return itemView;
    }
}
