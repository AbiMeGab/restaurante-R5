package com.example.restaurante.vista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurante.R;
import com.example.restaurante.modelo.entidad.Comida;
import com.example.restaurante.vistamodelo.VMFormComida;
import com.google.android.material.appbar.MaterialToolbar;

public class ActividadDetalleComida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_comida);

        TextView tvPrecio = findViewById(R.id.tv_precio);
        TextView tvNombre = findViewById(R.id.tv_nombre);
        TextView tvDescripcion = findViewById(R.id.tv_descripcion);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        int comidaId = getIntent().getIntExtra(ActividadListaComida.EXTRA_COMIDA_ID, -1);

        VMFormComida viewModel = new ViewModelProvider(this).get(VMFormComida.class);
        Comida comida = viewModel.obtenerPorId(comidaId);

        if (comida != null) {
            toolbar.setTitle(comida.getNombre());
            tvPrecio.setText(String.format("$ %,.2f", comida.getPrecio()));
            tvNombre.setText(comida.getNombre());
            tvDescripcion.setText(comida.getDescripcion());
        }
    }
}
