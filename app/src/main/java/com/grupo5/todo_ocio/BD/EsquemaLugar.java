package com.grupo5.todo_ocio.BD;

import android.provider.BaseColumns;

public class EsquemaLugar {

    public static abstract class EntradaLugar implements BaseColumns {
        public static final String NOMBRE_TABLE = "Lugar";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String DESCRIPCION = "descripcion";
        public static final String PUNTUACION = "puntuacion";
        public static final String CATEGORIA = "categoria";
        public static final String LONGITUD = "longitud";
        public static final String LATITUD = "latitud";
        public static final String IMAGEN = "imagen";

    }

}