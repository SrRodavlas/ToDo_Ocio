package com.grupo5.todo_ocio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grupo5.todo_ocio.list.AdaptadorCategoria;
import com.grupo5.todo_ocio.list.Categoria;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        ArrayList<Categoria> categoria = new ArrayList<Categoria>();
        categoria.add(new Categoria("0", "Elemento", "Descripcion", getDrawable(R.drawable.ic_action_save)));
        categoria.add(new Categoria("1", "Elemento2", "Descripcion2", getDrawable(R.drawable.ic_action_save)));

        ListView lv = (ListView) findViewById(R.id.lst_categorias);
        AdaptadorCategoria adapter = new AdaptadorCategoria(this, categoria);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                //CODIGO AQUI

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
    public void ejecutar_nuevo(View view){
        Intent i = new Intent(this, Nuevo.class);
        startActivity(i);
    }

    //Funcionalidad del botón provisional (esto se haría pulsando en un elemento de la lista
    public void ejeccutar_ver(View view){
        Intent i = new Intent(this, Ver.class);
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
