package com.example.restaurante.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurante.R;
import com.example.restaurante.adaptador.AdaptadorRestaurante;
import com.example.restaurante.vistamodelo.VMListaRestaurante;
import com.google.android.material.appbar.MaterialToolbar;

public class ActividadListaRestaurante extends AppCompatActivity {

    public static final String EXTRA_RESTAURANTE_ID = "EXTRA_RESTAURANTE_ID";
    public static final String EXTRA_RESTAURANTE_NOMBRE = "EXTRA_RESTAURANTE_NOMBRE";

    private VMListaRestaurante viewModel;
    private AdaptadorRestaurante adaptador;
    private ListView lvRestaurantes;
    private TextView tvVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurante);

        lvRestaurantes = findViewById(R.id.lv_restaurantes);
        tvVacio = findViewById(R.id.tv_vacio);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        adaptador = new AdaptadorRestaurante();
        lvRestaurantes.setAdapter(adaptador);
        registerForContextMenu(lvRestaurantes);

        viewModel = new ViewModelProvider(this).get(VMListaRestaurante.class);
        viewModel.getRestaurantes().observe(this, lista -> {
            adaptador.actualizarDatos(lista);
            tvVacio.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);
            lvRestaurantes.setVisibility(lista.isEmpty() ? View.GONE : View.VISIBLE);
        });

        lvRestaurantes.setOnItemClickListener((padre, vista, posicion, id) -> {
            var restaurante = adaptador.getItem(posicion);
            Intent intent = new Intent(this, ActividadListaComida.class);
            intent.putExtra(EXTRA_RESTAURANTE_ID, restaurante.getId());
            intent.putExtra(EXTRA_RESTAURANTE_NOMBRE, restaurante.getNombre());
            startActivity(intent);
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.accion_agregar) {
                startActivity(new Intent(this, ActividadAgregarRestaurante.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.cargar();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.accion_editar && info != null) {
            var restaurante = adaptador.getItem(info.position);
            Intent intent = new Intent(this, ActividadEditarRestaurante.class);
            intent.putExtra(EXTRA_RESTAURANTE_ID, restaurante.getId());
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
