package com.example.restaurante.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.restaurante.modelo.entidad.Comida;
import com.example.restaurante.modelo.repositorio.RepositorioComida;

public class VMFormComida extends AndroidViewModel {

    private final RepositorioComida repositorio;

    public VMFormComida(@NonNull Application application) {
        super(application);
        repositorio = new RepositorioComida(application);
    }

    public Comida obtenerPorId(int id) {
        return repositorio.obtenerPorId(id);
    }

    public boolean insertar(Comida comida) {
        if (comida.getNombre() == null || comida.getNombre().trim().isEmpty()) {
            return false;
        }
        return repositorio.insertar(comida) > 0;
    }

    public boolean actualizar(Comida comida) {
        if (comida.getNombre() == null || comida.getNombre().trim().isEmpty()) {
            return false;
        }
        return repositorio.actualizar(comida) > 0;
    }

    public boolean eliminar(int id) {
        return repositorio.eliminar(id) > 0;
    }
}
