package com.grupo5.todo_ocio.list;

import android.graphics.drawable.Drawable;

public class Categoria {
    private String title;
    private String id;
    private String descripcion;
    private Drawable imagen;

    public Categoria() {
        super();
    }

    public Categoria(String id, String title, String descripcion, Drawable imagen) {
        super();
        this.title = title;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}
}
