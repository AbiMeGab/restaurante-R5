package com.example.restaurante.modelo.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AyudanteBD extends SQLiteOpenHelper {

    public AyudanteBD(Context contexto) {
        super(contexto, ContratoBD.NOMBRE_BD, null, ContratoBD.VERSION_BD);
    }

    @Override
    public void onConfigure(SQLiteDatabase bd) {
        super.onConfigure(bd);
        bd.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(ContratoBD.TablaRestaurante.SQL_CREAR);
        bd.execSQL(ContratoBD.TablaComida.SQL_CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionAnterior, int versionNueva) {
        bd.execSQL(ContratoBD.TablaComida.SQL_ELIMINAR);
        bd.execSQL(ContratoBD.TablaRestaurante.SQL_ELIMINAR);
        onCreate(bd);
    }
}
