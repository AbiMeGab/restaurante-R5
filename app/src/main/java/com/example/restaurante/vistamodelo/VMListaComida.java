package com.example.restaurante.vistamodelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurante.modelo.entidad.Comida;
import com.example.restaurante.modelo.repositorio.RepositorioComida;

import java.util.List;

public class VMListaComida extends AndroidViewModel {

    private final RepositorioComida repositorio;
    private final MutableLiveData<List<Comida>> comidas = new MutableLiveData<>();

    public VMListaComida(@NonNull Application application) {
        super(application);
        repositorio = new RepositorioComida(application);
    }

    public LiveData<List<Comida>> getComidas() {
        return comidas;
    }

    public void cargar(int restauranteId, String tipo) {
        comidas.setValue(repositorio.obtenerPorRestauranteYTipo(restauranteId, tipo));
    }
}
