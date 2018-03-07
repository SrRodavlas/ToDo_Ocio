package com.grupo5.todo_ocio.list;

import android.graphics.drawable.Drawable;

public class Lugar {
    private int id;
    private String nombre, descripcion, categoria;
    private float puntuacion;
    private double longitud, latitud;
    private String imagen;

    public Lugar() {
        super();
    }

    public Lugar(int id, String nombre, String descripcion, String categoria, float puntuacion,
            double longitud, double latitud, String imagen) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.puntuacion = puntuacion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.imagen = imagen;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public float getPuntuacion() { return puntuacion; }

    public void setPuntuacion(float puntuacion) { this.puntuacion = puntuacion; }

    public double getLongitud() { return longitud; }

    public void setLongitud(double longitud) { this.longitud = longitud; }

    public double getLatitud() { return latitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    public String getImagen() {return imagen; }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
