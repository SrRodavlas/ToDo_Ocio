package com.grupo5.todo_ocio.BD;

/**
 * Created by Luisao on 06/03/2018.
 */


import android.content.ContentValues;
import android.database.Cursor;

import com.grupo5.todo_ocio.BD.EsquemaLugar.EntradaLugar;

import java.util.UUID;

public class Lugar {
    private String id;
    private String nombre;
    private String biografia;
    private String puntuacion;
    private String tag;
    private String longitud;
    private String latitud;
    private String imagen;

    public Lugar(String nombre, String biografia, String puntuacion, String tag, String longitud, String latitud, String imagen) {
        id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.biografia = biografia;
        this.puntuacion = puntuacion;
        this.tag = tag;
        this.longitud = longitud;
        this.latitud = latitud;
        this.imagen = imagen;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public ContentValues toContentValues() {
        ContentValues valores = new ContentValues();
        valores.put(EntradaLugar.ID, id);
        valores.put(EntradaLugar.NOMBRE, nombre);
        valores.put(EntradaLugar.BIO, biografia);
        valores.put(EntradaLugar.PUNTUACION, puntuacion);
        valores.put(EntradaLugar.TAG, tag);
        valores.put(EntradaLugar.LONGITUD, longitud);
        valores.put(EntradaLugar.LATITUD, latitud);
        valores.put(EntradaLugar.IMAGEN, imagen);
        return valores;

    }

    public Lugar(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(EntradaLugar.ID));
        nombre = cursor.getString(cursor.getColumnIndex(EntradaLugar.NOMBRE));
        biografia = cursor.getString(cursor.getColumnIndex(EntradaLugar.BIO));
        puntuacion = cursor.getString(cursor.getColumnIndex(EntradaLugar.PUNTUACION));
        tag = cursor.getString(cursor.getColumnIndex(EntradaLugar.TAG));
        longitud = cursor.getString(cursor.getColumnIndex(EntradaLugar.LONGITUD));
        latitud = cursor.getString(cursor.getColumnIndex(EntradaLugar.LATITUD));
        imagen = cursor.getString(cursor.getColumnIndex(EntradaLugar.IMAGEN));
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "ID='" + id + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", Descripcion='" + biografia + '\'' +
                ", Horario='" + puntuacion + '\'' +
                ", Categoria='" + tag + '\'' +
                ", Longitud='" + longitud + '\'' +
                ", Latitud='" + latitud + '\'' +
                ", Foto='" + imagen + '\'' +
                '}';
    }


}