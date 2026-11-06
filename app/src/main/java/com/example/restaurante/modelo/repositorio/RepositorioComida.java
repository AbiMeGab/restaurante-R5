package com.example.restaurante.modelo.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurante.modelo.bd.AyudanteBD;
import com.example.restaurante.modelo.bd.ContratoBD.TablaComida;
import com.example.restaurante.modelo.entidad.Comida;

import java.util.ArrayList;
import java.util.List;

public class RepositorioComida {

    private final AyudanteBD ayudanteBD;

    public RepositorioComida(Context contexto) {
        ayudanteBD = new AyudanteBD(contexto);
    }

    public List<Comida> obtenerPorRestauranteYTipo(int restauranteId, String tipo) {
        List<Comida> lista = new ArrayList<>();
        SQLiteDatabase bd = ayudanteBD.getReadableDatabase();
        Cursor cursor = bd.query(TablaComida.NOMBRE_TABLA,
                null,
                TablaComida.COL_RESTAURANTE_ID + " = ? AND " + TablaComida.COL_TIPO + " = ?",
                new String[]{String.valueOf(restauranteId), tipo},
                null, null,
                TablaComida.COL_NOMBRE + " ASC");

        while (cursor.moveToNext()) {
            lista.add(cursorAComida(cursor));
        }
        cursor.close();
        return lista;
    }

    public Comida obtenerPorId(int id) {
        SQLiteDatabase bd = ayudanteBD.getReadableDatabase();
        Cursor cursor = bd.query(TablaComida.NOMBRE_TABLA,
                null,
                TablaComida.COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        Comida c = null;
        if (cursor.moveToFirst()) {
            c = cursorAComida(cursor);
        }
        cursor.close();
        return c;
    }

    public long insertar(Comida comida) {
        SQLiteDatabase bd = ayudanteBD.getWritableDatabase();
        ContentValues valores = crearValores(comida);
        return bd.insert(TablaComida.NOMBRE_TABLA, null, valores);
    }

    public int actualizar(Comida comida) {
        SQLiteDatabase bd = ayudanteBD.getWritableDatabase();
        ContentValues valores = crearValores(comida);
        return bd.update(TablaComida.NOMBRE_TABLA, valores,
                TablaComida.COL_ID + " = ?",
                new String[]{String.valueOf(comida.getId())});
    }

    public int eliminar(int id) {
        SQLiteDatabase bd = ayudanteBD.getWritableDatabase();
        return bd.delete(TablaComida.NOMBRE_TABLA,
                TablaComida.COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    private ContentValues crearValores(Comida comida) {
        ContentValues valores = new ContentValues();
        valores.put(TablaComida.COL_RESTAURANTE_ID, comida.getRestauranteId());
        valores.put(TablaComida.COL_NOMBRE, comida.getNombre());
        valores.put(TablaComida.COL_PRECIO, comida.getPrecio());
        valores.put(TablaComida.COL_DESCRIPCION, comida.getDescripcion());
        valores.put(TablaComida.COL_TIPO, comida.getTipo());
        return valores;
    }

    private Comida cursorAComida(Cursor cursor) {
        return new Comida(
                cursor.getInt(cursor.getColumnIndexOrThrow(TablaComida.COL_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(TablaComida.COL_RESTAURANTE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(TablaComida.COL_NOMBRE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(TablaComida.COL_PRECIO)),
                cursor.getString(cursor.getColumnIndexOrThrow(TablaComida.COL_DESCRIPCION)),
                cursor.getString(cursor.getColumnIndexOrThrow(TablaComida.COL_TIPO))
        );
    }
}
