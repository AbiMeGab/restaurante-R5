package com.example.restaurante.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurante.R;
import com.example.restaurante.modelo.entidad.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorRestaurante extends BaseAdapter {

    private List<Restaurante> lista = new ArrayList<>();

    public void actualizarDatos(List<Restaurante> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Restaurante getItem(int posicion) {
        return lista.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return lista.get(posicion).getId();
    }

    @Override
    public View getView(int posicion, View vistaReusable, ViewGroup padre) {
        ViewHolder holder;
        if (vistaReusable == null) {
            vistaReusable = LayoutInflater.from(padre.getContext())
                    .inflate(R.layout.item_restaurante, padre, false);
            holder = new ViewHolder();
            holder.tvNombre = vistaReusable.findViewById(R.id.tv_nombre_restaurante);
            vistaReusable.setTag(holder);
        } else {
            holder = (ViewHolder) vistaReusable.getTag();
        }

        Restaurante restaurante = getItem(posicion);
        holder.tvNombre.setText(restaurante.getNombre());

        return vistaReusable;
    }

    private static class ViewHolder {
        TextView tvNombre;
    }
}
