package com.grupo5.todo_ocio.BD;


public class Estructura_BBDD {
            private static final String TABLE_NAME = "Lugares";
            public static final String id = "id";
            private static final String nombre = "Nombre";
            private static final String bio = "Bio";
            private static final String puntuacion = "Puntuación";
            private static final String tag = "Tag";
            private static final String longitud= "Longitud";
            private static final String latitud = "Latitud";
            private static final String imagen = "Imagen";

    private static final String TEXT_TYPE = "TEXT";
    private static final String COMMA_SEP = ",";
    private static final float GPS_TYPE = (float) 0.0;

    protected static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                        Estructura_BBDD.id + " INTEGER PRIMARY KEY," +
                        Estructura_BBDD.nombre + TEXT_TYPE + COMMA_SEP +
                        Estructura_BBDD.bio + TEXT_TYPE + COMMA_SEP +
                        Estructura_BBDD.puntuacion + TEXT_TYPE + COMMA_SEP +
                        Estructura_BBDD.tag + TEXT_TYPE + COMMA_SEP +
                        Estructura_BBDD.longitud + GPS_TYPE + COMMA_SEP +
                        Estructura_BBDD.latitud+ GPS_TYPE + COMMA_SEP +
                        Estructura_BBDD.imagen + TEXT_TYPE;

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;
}
