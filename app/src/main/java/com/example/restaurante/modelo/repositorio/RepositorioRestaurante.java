package com.example.restaurante.modelo.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurante.modelo.bd.AyudanteBD;
import com.example.restaurante.modelo.bd.ContratoBD.TablaRestaurante;
import com.example.restaurante.modelo.entidad.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class RepositorioRestaurante {

    private final AyudanteBD ayudanteBD;

    public RepositorioRestaurante(Context contexto) {
        ayudanteBD = new AyudanteBD(contexto);
    }

    public List<Restaurante> obtenerTodos() {
        List<Restaurante> lista = new ArrayList<>();
        SQLiteDatabase bd = ayudanteBD.getReadableDatabase();
        Cursor cursor = bd.query(TablaRestaurante.NOMBRE_TABLA,
                null, null, null, null, null,
                TablaRestaurante.COL_NOMBRE + " ASC");

        while (cursor.moveToNext()) {
            Restaurante r = new Restaurante(
                    cursor.getInt(cursor.getColumnIndexOrThrow(TablaRestaurante.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TablaRestaurante.COL_NOMBRE))
            );
            lista.add(r);
        }
        cursor.close();
        return lista;
    }

    public Restaurante obtenerPorId(int id) {
        SQLiteDatabase bd = ayudanteBD.getReadableDatabase();
        Cursor cursor = bd.query(TablaRestaurante.NOMBRE_TABLA,
                null,
                TablaRestaurante.COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        Restaurante r = null;
        if (cursor.moveToFirst()) {
            r = new Restaurante(
                    cursor.getInt(cursor.getColumnIndexOrThrow(TablaRestaurante.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TablaRestaurante.COL_NOMBRE))
            );
        }
        cursor.close();
        return r;
    }

    public long insertar(Restaurante restaurante) {
        SQLiteDatabase bd = ayudanteBD.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(TablaRestaurante.COL_NOMBRE, restaurante.getNombre());
        return bd.insert(TablaRestaurante.NOMBRE_TABLA, null, valores);
    }

    public int actualizar(Restaurante restaurante) {
        SQLiteDatabase bd = ayudanteBD.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(TablaRestaurante.COL_NOMBRE, restaurante.getNombre());
        return bd.update(TablaRestaurante.NOMBRE_TABLA, valores,
                TablaRestaurante.COL_ID + " = ?",
                new String[]{String.valueOf(restaurante.getId())});
    }

    public int eliminar(int id) {
        SQLiteDatabase bd = ayudanteBD.getWritableDatabase();
        return bd.delete(TablaRestaurante.NOMBRE_TABLA,
                TablaRestaurante.COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
