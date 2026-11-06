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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class ActividadEditarComida extends AppCompatActivity {

    private VMFormComida viewModel;
    private TextInputEditText etNombre;
    private TextInputEditText etPrecio;
    private TextInputEditText etDescripcion;
    private Spinner spTipo;
    private int comidaId;
    private int restauranteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_comida);

        etNombre = findViewById(R.id.et_nombre);
        etPrecio = findViewById(R.id.et_precio);
        etDescripcion = findViewById(R.id.et_descripcion);
        spTipo = findViewById(R.id.sp_tipo);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(VMFormComida.class);

        comidaId = getIntent().getIntExtra(ActividadListaComida.EXTRA_COMIDA_ID, -1);

        Comida comida = viewModel.obtenerPorId(comidaId);
        if (comida != null) {
            restauranteId = comida.getRestauranteId();
            etNombre.setText(comida.getNombre());
            etPrecio.setText(String.valueOf(comida.getPrecio()));
            etDescripcion.setText(comida.getDescripcion());
            spTipo.setSelection(TipoComidaUtil.obtenerPosicionSpinner(comida.getTipo()));
        }

        findViewById(R.id.btn_actualizar).setOnClickListener(v -> actualizar());
        findViewById(R.id.btn_eliminar).setOnClickListener(v -> confirmarEliminar());
    }

    private void actualizar() {
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

        Comida comida = new Comida(comidaId, restauranteId, nombre, precio, descripcion, tipo);

        if (viewModel.actualizar(comida)) {
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
                    if (viewModel.eliminar(comidaId)) {
                        Toast.makeText(this, R.string.mensaje_eliminado, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private String obtenerTexto(TextInputEditText campo) {
        return campo.getText() != null ? campo.getText().toString().trim() : "";
    }
}
