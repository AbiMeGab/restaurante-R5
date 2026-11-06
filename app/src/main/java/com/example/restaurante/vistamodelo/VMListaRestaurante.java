package com.example.restaurante.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurante.modelo.entidad.Restaurante;
import com.example.restaurante.modelo.repositorio.RepositorioRestaurante;

import java.util.List;

public class VMListaRestaurante extends AndroidViewModel {

    private final RepositorioRestaurante repositorio;
    private final MutableLiveData<List<Restaurante>> restaurantes = new MutableLiveData<>();

    public VMListaRestaurante(@NonNull Application application) {
        super(application);
        repositorio = new RepositorioRestaurante(application);
    }

    public LiveData<List<Restaurante>> getRestaurantes() {
        return restaurantes;
    }

    public void cargar() {
        restaurantes.setValue(repositorio.obtenerTodos());
    }
}
