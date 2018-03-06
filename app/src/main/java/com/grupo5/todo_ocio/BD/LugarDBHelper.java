package com.grupo5.todo_ocio.BD;

/**
 * Created by Luisao on 06/03/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.grupo5.todo_ocio.BD.EsquemaLugar.EntradaLugar;

public class LugarDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lugares.db";

    public LugarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Comandos SQL. Crear tablas...
        sqLiteDatabase.execSQL("CREATE TABLE " + EsquemaLugar.EntradaLugar.NOMBRE_TABLE + "  ("
                + EsquemaLugar.EntradaLugar._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EsquemaLugar.EntradaLugar.ID + " TEXT NOT NULL,"
                + EsquemaLugar.EntradaLugar.NOMBRE + " TEXT NOT NULL,"
                + EsquemaLugar.EntradaLugar.BIO + " TEXT NOT NULL,"
                + EsquemaLugar.EntradaLugar.TAG + " TEXT NOT NULL,"
                + EsquemaLugar.EntradaLugar.PUNTUACION + " INTEGER,"
                + EsquemaLugar.EntradaLugar.LONGITUD + " INTEGER,"
                + EsquemaLugar.EntradaLugar.LATITUD + " INTEGER,"
                + EsquemaLugar.EntradaLugar.IMAGEN + " TEXT,"
                + "UNIQUE (" + EsquemaLugar.EntradaLugar.ID + "))");

        //Contenedor de valores
        //ContentValues values = new ContentValues();


        mockData(sqLiteDatabase);

    }

    private void mockData(SQLiteDatabase database) {
        //mockLugar(database, new Lugar("Plaza de España, Sevilla", "Conjunto arquitectonico proyectado por Anibal Gonzalez", "9:00-9:00", "Monumentos", "4.5", "-5.985924", "37.376954", ""));
        //mockLugar(database, new Lugar("Presta Shop", "Comercia con ropa", "09:00-20:00", "Tiendas", "3.5", "-5.994504", "37.404661", ""));

    }

    public long mockLugar(SQLiteDatabase db, Lugar lugar) {
        return db.insert(
                EntradaLugar.NOMBRE_TABLE,
                null,
                lugar.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int antigua, int nueva) {

    }

    public long guardarLugar(Lugar lugar) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                EntradaLugar.NOMBRE_TABLE, null, lugar.toContentValues());
    }

    public Cursor getAllLugares() {

        if (Lista.spinner.getSelectedItemPosition() == 0) {
            return getReadableDatabase()
                    .query(
                            EntradaLugar.NOMBRE_TABLE,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null);
        } else {
            String categoria = "";
            int poss = Lista.spinner.getSelectedItemPosition();
            if (poss == 1) {
                categoria = "Monumentos";
            } else if (poss == 2) {
                categoria = "Parques";
            } else if (poss == 3) {
                categoria = "Tiendas";
            }
            return getReadableDatabase().query(
                    EntradaLugar.NOMBRE_TABLE,
                    null,
                    EntradaLugar.TAG+ " LIKE ?",
                    new String[]{categoria},
                    null,
                    null,
                    null);
        }
    }

    public Cursor getAllLugaresMapa() {

        if (VerMapa.spnCategorias.getSelectedItemPosition() == 0) {
            return getReadableDatabase()
                    .query(
                            EntradaLugar.NOMBRE_TABLE,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null);
        } else {
            String categoria = "";
            int poss = VerMapa.spnCategorias.getSelectedItemPosition();
            if (poss == 1) {
                categoria = "Monumentos";
            } else if (poss == 2) {
                categoria = "Parques";
            } else if (poss == 3) {
                categoria = "Tiendas";
            }
            return getReadableDatabase().query(
                    EntradaLugar.NOMBRE_TABLE,
                    null,
                    EntradaLugar.TAG + " LIKE ?",
                    new String[]{categoria},
                    null,
                    null,
                    null);
        }
    }

    public Cursor getLugarPorID(String lugarID) {
        Cursor c = getReadableDatabase().query(
                EntradaLugar.NOMBRE_TABLE,
                null,
                EntradaLugar.ID + " LIKE ?",
                new String[]{lugarID},
                null,
                null,
                null);
        return c;
    }

    public int borrarLugar(String lugarID) {
        return getWritableDatabase().delete(
                EntradaLugar.NOMBRE_TABLE,
                EntradaLugar.ID+ " LIKE ?",
                new String[]{lugarID});
    }

    public int updateLugar(Lugar lugar, String lugarID) {
        return getWritableDatabase().update(
                EntradaLugar.NOMBRE_TABLE,
                lugar.toContentValues(),
                EntradaLugar.ID + " LIKE ?",
                new String[]{lugarID}
        );
    }

}