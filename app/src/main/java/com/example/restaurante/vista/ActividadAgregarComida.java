package com.example.restaurante.vista;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurante.R;
import com.example.restaurante.modelo.entidad.Comida;
import com.example.restaurante.util.TipoComidaUtil;
import com.example.restaurante.vistamodelo.VMFormComida;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class ActividadAgregarComida extends AppCompatActivity {

    private VMFormComida viewModel;
    private TextInputEditText etNombre;
    private TextInputEditText etPrecio;
    private TextInputEditText etDescripcion;
    private Spinner spTipo;
    private int restauranteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_comida);

        etNombre = findViewById(R.id.et_nombre);
        etPrecio = findViewById(R.id.et_precio);
        etDescripcion = findViewById(R.id.et_descripcion);
        spTipo = findViewById(R.id.sp_tipo);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        restauranteId = getIntent().getIntExtra(
                ActividadListaRestaurante.EXTRA_RESTAURANTE_ID, -1);

        viewModel = new ViewModelProvider(this).get(VMFormComida.class);

        findViewById(R.id.btn_registrar).setOnClickListener(v -> registrar());
    }

    private void registrar() {
        String nombre = obtenerTexto(etNombre);
        String precioStr = obtenerTexto(etPrecio);
        String descripcion = obtenerTexto(etDescripcion);

        if (nombre.isEmpty()) {
            Toast.makeText(this, R.string.error_nombre_vacio, Toast.LENGTH_SHORT).show();
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.error_precio_invalido, Toast.LENGTH_SHORT).show();
            return;
        }

        String tipo = TipoComidaUtil.obtenerTipoBD(spTipo.getSelectedItemPosition());

        Comida comida = new Comida();
        comida.setRestauranteId(restauranteId);
        comida.setNombre(nombre);
        comida.setPrecio(precio);
        comida.setDescripcion(descripcion);
        comida.setTipo(tipo);

        if (viewModel.insertar(comida)) {
            Toast.makeText(this, R.string.mensaje_registrado, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_registro, Toast.LENGTH_SHORT).show();
        }
    }

    private String obtenerTexto(TextInputEditText campo) {
        return campo.getText() != null ? campo.getText().toString().trim() : "";
    }
}
