package com.grupo5.todo_ocio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grupo5.todo_ocio.list.AdaptadorLugar;
import com.grupo5.todo_ocio.list.Lugar;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {

    public static ArrayList<Lugar> lugares;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //Carga la lista con la base de datos (prueba temporal hasta implementacion de base de datos)
        lugares = new ArrayList<Lugar>();
        //TODO Sacar de la base de datos los registros para la lista
        lugares.add(new Lugar(0, "Elemento", "Descripcion", "Parque",(float) 5.0, 1.0, 1.0, "drawable/ic_action_save.png"));
        lugares.add(new Lugar(1, "Elemento2", "Descripcion2", "Cine",(float) 2.5,1.0, 1.0, "drawable/ic_action_save.png"));

        ListView lv = (ListView) findViewById(R.id.lst_categorias);
        AdaptadorLugar adaptador = new AdaptadorLugar(this, lugares);
        lv.setAdapter(adaptador);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Intent i = new Intent(context, Ver.class);
                i.putExtra("posicion", pos);
                startActivity(i);
            }
        });
        //getActionBar().setDisplayHomeAsUpEnabled(true);  //hace que pete?
    }

    //Funcionalidad del botón buscar
    public void ejeccutar_mapa(View view){
      Intent i = new Intent(this, Mapa.class);
      startActivity(i);
    }

    //Funcionalidad del botón flotante (nuevo)
    public void nuevo(View view){
        Intent i = new Intent(this, Editar.class);
        startActivity(i);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
