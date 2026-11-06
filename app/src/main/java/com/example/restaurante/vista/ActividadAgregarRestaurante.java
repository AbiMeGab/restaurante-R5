package com.example.restaurante.vista;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurante.R;
import com.example.restaurante.vistamodelo.VMFormRestaurante;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class ActividadAgregarRestaurante extends AppCompatActivity {

    private VMFormRestaurante viewModel;
    private TextInputEditText etNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_restaurante);

        etNombre = findViewById(R.id.et_nombre);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(VMFormRestaurante.class);

        findViewById(R.id.btn_registrar).setOnClickListener(v -> registrar());
    }

    private void registrar() {
        String nombre = etNombre.getText() != null ? etNombre.getText().toString() : "";

        if (nombre.trim().isEmpty()) {
            Toast.makeText(this, R.string.error_nombre_vacio, Toast.LENGTH_SHORT).show();
            return;
        }

        if (viewModel.insertar(nombre)) {
            Toast.makeText(this, R.string.mensaje_registrado, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_registro, Toast.LENGTH_SHORT).show();
        }
    }
}
