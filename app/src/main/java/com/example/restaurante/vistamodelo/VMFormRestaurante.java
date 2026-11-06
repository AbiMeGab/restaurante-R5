package com.example.restaurante.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.restaurante.modelo.entidad.Restaurante;
import com.example.restaurante.modelo.repositorio.RepositorioRestaurante;

public class VMFormRestaurante extends AndroidViewModel {

    private final RepositorioRestaurante repositorio;

    public VMFormRestaurante(@NonNull Application application) {
        super(application);
        repositorio = new RepositorioRestaurante(application);
    }

    public Restaurante obtenerPorId(int id) {
        return repositorio.obtenerPorId(id);
    }

    public boolean insertar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        Restaurante r = new Restaurante();
        r.setNombre(nombre.trim());
        return repositorio.insertar(r) > 0;
    }

    public boolean actualizar(int id, String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        Restaurante r = new Restaurante(id, nombre.trim());
        return repositorio.actualizar(r) > 0;
    }

    public boolean eliminar(int id) {
        return repositorio.eliminar(id) > 0;
    }
}
