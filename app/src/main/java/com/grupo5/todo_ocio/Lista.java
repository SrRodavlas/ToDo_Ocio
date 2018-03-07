package com.grupo5.todo_ocio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.grupo5.todo_ocio.BD.BBDD_Metodos_helper;
import com.grupo5.todo_ocio.BD.EsquemaLugar;
import com.grupo5.todo_ocio.list.AdaptadorLugar;
import com.grupo5.todo_ocio.list.Lugar;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {

    public static BBDD_Metodos_helper sqlite;
    public static ArrayList<Lugar> lugares;
    private Activity activity = this;
    private AdaptadorLugar adaptador;
    private Spinner spin_filtrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //Carga la lista con la base de datos (prueba temporal hasta implementacion de base de datos)
        lugares = new ArrayList<Lugar>();
        //TODO Sacar de la base de datos los registros para la lista
        sqlite = new BBDD_Metodos_helper(this);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        Cursor puntero = db.rawQuery("SELECT * from " + EsquemaLugar.EntradaLugar.NOMBRE_TABLE, null);
        if(puntero.moveToFirst()){
            do{
                lugares.add(new Lugar(puntero.getInt(0), puntero.getString(1),
                        puntero.getString(2), puntero.getString(3), puntero.getFloat(4)
                        , puntero.getDouble(5), puntero.getDouble(6)
                        , puntero.getString(7)));
            } while(puntero.moveToNext());
        }

        db.close();

        /*lugares.add(new Lugar(0, "Elemento", "Descripcion", "Parque",(float) 5.0, 37.404168,  -5.971336, "drawable/ic_action_save.png"));
        lugares.add(new Lugar(1, "Elemento2", "Descripcion2", "Cine",(float) 2.5,37.394442, -5.983640, "drawable/ic_action_save.png"));
        lugares.add(new Lugar(1, "Elemento3", "Descripcion3", "Restaurante",(float) 3.5,37.392736, -5.996429, "drawable/ic_action_save.png"));*/

        ListView lv = findViewById(R.id.lst_categorias);
        adaptador = new AdaptadorLugar(activity, new ArrayList<Lugar>());
        lv.setAdapter(adaptador);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Intent i = new Intent(activity, Ver.class);
                i.putExtra("posicion", pos);
                startActivity(i);
            }
        });

        spin_filtrado = (Spinner) findViewById(R.id.spin_filtrado);
        String[] letra = {getString(R.string.spin_filtradoTodo), getString(R.string.spin_filtradoCine), getString(R.string.spin_filtradoParque), getString(R.string.spin_filtradoRestaurante)};
        spin_filtrado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        spin_filtrado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    adaptador.limpiar();
                    adaptador.añadirTodo(lugares);
                } else if(position == 1){ //Filtro de elementos de categoria cines
                    ArrayList<Lugar> lugaresCines = new ArrayList<>();
                    for(int x = 0; x < lugares.size(); x++){
                        if(lugares.get(x).getCategoria().equals("Cine")){
                            lugaresCines.add(lugares.get(x));
                        }
                    }
                    adaptador.limpiar();
                    adaptador.añadirTodo(lugaresCines);
                } else if(position == 2){ //Filtro de elementos de categoria parques
                    ArrayList<Lugar> lugaresParques = new ArrayList<>();
                    for(int x = 0; x < lugares.size(); x++){
                        if(lugares.get(x).getCategoria().equals("Parque")){
                            lugaresParques.add(lugares.get(x));
                        }
                    }
                    adaptador.limpiar();
                    adaptador.añadirTodo(lugaresParques);
                } else { //Filtro de elementos de categoria restaurantes
                    ArrayList<Lugar> lugaresRestaurantes = new ArrayList<>();
                    for(int x = 0; x < lugares.size(); x++){
                        if(lugares.get(x).getCategoria().equals("Restaurante")){
                            lugaresRestaurantes.add(lugares.get(x));
                        }
                    }
                    adaptador.limpiar();
                    adaptador.añadirTodo(lugaresRestaurantes);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //getActionBar().setDisplayHomeAsUpEnabled(true);  //hace que pete?
    }

    //Funcionalidad del botón buscar
    public void mapa(View view){
      Intent i = new Intent(this, Mapa.class);
      i.putExtra("categoria", (String) spin_filtrado.getSelectedItem());
      startActivity(i);
    }

    //Funcionalidad del botón flotante (nuevo)
    public void nuevo(View view){
        Intent i = new Intent(this, Editar.class);
        i.putExtra("nuevo", true);
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
