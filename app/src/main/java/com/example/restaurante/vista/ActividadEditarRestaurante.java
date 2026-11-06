package com.example.restaurante.vista;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurante.R;
import com.example.restaurante.modelo.entidad.Restaurante;
import com.example.restaurante.vistamodelo.VMFormRestaurante;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class ActividadEditarRestaurante extends AppCompatActivity {

    private VMFormRestaurante viewModel;
    private TextInputEditText etNombre;
    private int restauranteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_restaurante);

        etNombre = findViewById(R.id.et_nombre);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(VMFormRestaurante.class);

        restauranteId = getIntent().getIntExtra(ActividadListaRestaurante.EXTRA_RESTAURANTE_ID, -1);

        Restaurante restaurante = viewModel.obtenerPorId(restauranteId);
        if (restaurante != null) {
            etNombre.setText(restaurante.getNombre());
        }

        findViewById(R.id.btn_actualizar).setOnClickListener(v -> actualizar());
        findViewById(R.id.btn_eliminar).setOnClickListener(v -> confirmarEliminar());
    }

    private void actualizar() {
        String nombre = etNombre.getText() != null ? etNombre.getText().toString() : "";

        if (nombre.trim().isEmpty()) {
            Toast.makeText(this, R.string.error_nombre_vacio, Toast.LENGTH_SHORT).show();
            return;
        }

        if (viewModel.actualizar(restauranteId, nombre)) {
            Toast.makeText(this, R.string.mensaje_actualizado, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_registro, Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmarEliminar() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.confirmar_eliminar_titulo)
                .setMessage(R.string.confirmar_eliminar_mensaje)
                .setPositiveButton(R.string.si, (dialog, which) -> {
                    if (viewModel.eliminar(restauranteId)) {
                        Toast.makeText(this, R.string.mensaje_eliminado, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
