package com.grupo5.todo_ocio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Lista extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

       // getActionBar().setDisplayHomeAsUpEnabled(true);  //hace que pete
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
