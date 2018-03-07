package com.grupo5.todo_ocio.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.grupo5.todo_ocio.list.Lugar;

import static com.grupo5.todo_ocio.BD.EsquemaLugar.EntradaLugar;

public class BBDD_Metodos_helper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lugares.db";

    public BBDD_Metodos_helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EntradaLugar.NOMBRE_TABLE + " ("
            + EntradaLugar.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EntradaLugar.NOMBRE + " TEXT NOT NULL,"
            + EntradaLugar.DESCRIPCION + " TEXT NOT NULL,"
            + EntradaLugar.CATEGORIA + " TEXT NOT NULL,"
            + EntradaLugar.PUNTUACION + " FLOAT NOT NULL,"
            + EntradaLugar.LONGITUD + " DOUBLE NOT NULL,"
            + EntradaLugar.LATITUD + " DOUBLE NOT NULL,"
            + EntradaLugar.IMAGEN + " TEXT NOT NULL,"
            + "UNIQUE (" + EntradaLugar.ID + "))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + EntradaLugar.NOMBRE_TABLE);
        db.execSQL("CREATE TABLE " + EntradaLugar.NOMBRE_TABLE + " ("
                + EntradaLugar.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EntradaLugar.NOMBRE + " TEXT NOT NULL,"
                + EntradaLugar.DESCRIPCION + " TEXT NOT NULL,"
                + EntradaLugar.CATEGORIA + " TEXT NOT NULL,"
                + EntradaLugar.PUNTUACION + " FLOAT NOT NULL,"
                + EntradaLugar.LONGITUD + " DOUBLE NOT NULL,"
                + EntradaLugar.LATITUD + " DOUBLE NOT NULL,"
                + EntradaLugar.IMAGEN + " TEXT NOT NULL,"
                + "UNIQUE (" + EntradaLugar.ID + "))");
    }

    public void insertar(Lugar lugar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registrar = new ContentValues();

        registrar.put(EntradaLugar.NOMBRE, lugar.getNombre());
        registrar.put(EntradaLugar.DESCRIPCION, lugar.getDescripcion());
        registrar.put(EntradaLugar.CATEGORIA, lugar.getCategoria());
        registrar.put(EntradaLugar.PUNTUACION, lugar.getPuntuacion());
        registrar.put(EntradaLugar.LONGITUD, lugar.getLongitud());
        registrar.put(EntradaLugar.LATITUD, lugar.getLatitud());
        registrar.put(EntradaLugar.IMAGEN, lugar.getImagen());

        db.insert(EntradaLugar.NOMBRE_TABLE, null, registrar);
        db.close();
    }

    public void actualizar(Lugar lugar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registrar = new ContentValues();

        registrar.put(EntradaLugar.NOMBRE, lugar.getNombre());
        registrar.put(EntradaLugar.DESCRIPCION, lugar.getDescripcion());
        registrar.put(EntradaLugar.CATEGORIA, lugar.getCategoria());
        registrar.put(EntradaLugar.PUNTUACION, lugar.getPuntuacion());
        registrar.put(EntradaLugar.LONGITUD, lugar.getLongitud());
        registrar.put(EntradaLugar.LATITUD, lugar.getLatitud());
        registrar.put(EntradaLugar.IMAGEN, lugar.getImagen());

        db.update(EntradaLugar.NOMBRE_TABLE, registrar, EntradaLugar.ID + "=" + lugar.getId(), null);
        db.close();
    }

    public void borrar(Lugar lugar){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EntradaLugar.NOMBRE_TABLE, EntradaLugar.ID + "=" + lugar.getId(), null);
        db.close();
    }
}

