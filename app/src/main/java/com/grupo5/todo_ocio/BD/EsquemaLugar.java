package com.grupo5.todo_ocio.BD;

import android.provider.BaseColumns;

/**
 * Created by Luisao on 06/03/2018.
 */

public class EsquemaLugar {

    public static abstract class EntradaLugar implements BaseColumns {
        public static final String NOMBRE_TABLE = "Lugar";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String BIO = "bio";
        public static final String PUNTUACION = "puntuacion";
        public static final String TAG = "tag";
        public static final String LONGITUD = "longitud";
        public static final String LATITUD = "latitud";
        public static final String IMAGEN = "imagen";

    }

}