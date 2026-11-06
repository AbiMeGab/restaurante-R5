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
import com.example.restaurante.adaptador.AdaptadorComida;
import com.example.restaurante.util.TipoComidaUtil;
import com.example.restaurante.vistamodelo.VMListaComida;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class ActividadListaComida extends AppCompatActivity {

    public static final String EXTRA_COMIDA_ID = "EXTRA_COMIDA_ID";

    private VMListaComida viewModel;
    private AdaptadorComida adaptador;
    private int restauranteId;
    private String tipoActual = "food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comida);

        ListView lvComidas = findViewById(R.id.lv_comidas);
        TextView tvVacio = findViewById(R.id.tv_vacio);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        restauranteId = getIntent().getIntExtra(
                ActividadListaRestaurante.EXTRA_RESTAURANTE_ID, -1);
        String nombreRestaurante = getIntent().getStringExtra(
                ActividadListaRestaurante.EXTRA_RESTAURANTE_NOMBRE);

        toolbar.setTitle(nombreRestaurante != null ? nombreRestaurante : "");
        toolbar.setNavigationOnClickListener(v -> finish());

        adaptador = new AdaptadorComida();
        lvComidas.setAdapter(adaptador);
        registerForContextMenu(lvComidas);

        viewModel = new ViewModelProvider(this).get(VMListaComida.class);
        viewModel.getComidas().observe(this, lista -> {
            adaptador.actualizarDatos(lista);
            tvVacio.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);
            lvComidas.setVisibility(lista.isEmpty() ? View.GONE : View.VISIBLE);
        });

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_comida));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_bebidas));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_complementos));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tipoActual = TipoComidaUtil.obtenerTipoBDDesdeTab(tab.getPosition());
                viewModel.cargar(restauranteId, tipoActual);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        lvComidas.setOnItemClickListener((padre, vista, posicion, id) -> {
            var comida = adaptador.getItem(posicion);
            Intent intent = new Intent(this, ActividadDetalleComida.class);
            intent.putExtra(EXTRA_COMIDA_ID, comida.getId());
            startActivity(intent);
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.accion_agregar) {
                Intent intent = new Intent(this, ActividadAgregarComida.class);
                intent.putExtra(ActividadListaRestaurante.EXTRA_RESTAURANTE_ID, restauranteId);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.cargar(restauranteId, tipoActual);
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
            var comida = adaptador.getItem(info.position);
            Intent intent = new Intent(this, ActividadEditarComida.class);
            intent.putExtra(EXTRA_COMIDA_ID, comida.getId());
            startActivity(intent);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
