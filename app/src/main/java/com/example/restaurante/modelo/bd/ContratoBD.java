package com.example.restaurante.modelo.bd;

public final class ContratoBD {

    public static final String NOMBRE_BD = "restaurante.db";
    public static final int VERSION_BD = 1;

    private ContratoBD() {
    }

    public static final class TablaRestaurante {
        public static final String NOMBRE_TABLA = "restaurant";
        public static final String COL_ID = "_id";
        public static final String COL_NOMBRE = "name";

        public static final String SQL_CREAR =
                "CREATE TABLE " + NOMBRE_TABLA + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NOMBRE + " TEXT NOT NULL)";

        public static final String SQL_ELIMINAR =
                "DROP TABLE IF EXISTS " + NOMBRE_TABLA;

        private TablaRestaurante() {
        }
    }

    public static final class TablaComida {
        public static final String NOMBRE_TABLA = "food";
        public static final String COL_ID = "_id";
        public static final String COL_RESTAURANTE_ID = "restaurant_id";
        public static final String COL_NOMBRE = "name";
        public static final String COL_PRECIO = "price";
        public static final String COL_DESCRIPCION = "description";
        public static final String COL_TIPO = "type";

        public static final String SQL_CREAR =
                "CREATE TABLE " + NOMBRE_TABLA + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_RESTAURANTE_ID + " INTEGER NOT NULL, " +
                        COL_NOMBRE + " TEXT NOT NULL, " +
                        COL_PRECIO + " REAL NOT NULL, " +
                        COL_DESCRIPCION + " TEXT, " +
                        COL_TIPO + " TEXT CHECK(" + COL_TIPO +
                        " IN ('food','drink','complement')), " +
                        "FOREIGN KEY (" + COL_RESTAURANTE_ID + ") REFERENCES " +
                        TablaRestaurante.NOMBRE_TABLA + "(" + TablaRestaurante.COL_ID +
                        ") ON DELETE CASCADE)";

        public static final String SQL_ELIMINAR =
                "DROP TABLE IF EXISTS " + NOMBRE_TABLA;

        private TablaComida() {
        }
    }
}
