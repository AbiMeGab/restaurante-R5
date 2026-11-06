package com.example.restaurante.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.restaurante.R;
import com.example.restaurante.modelo.entidad.Comida;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorComida extends BaseAdapter {

    private List<Comida> lista = new ArrayList<>();

    public void actualizarDatos(List<Comida> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Comida getItem(int posicion) {
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
                    .inflate(R.layout.item_comida, padre, false);
            holder = new ViewHolder();
            holder.tvNombre = vistaReusable.findViewById(R.id.tv_nombre_comida);
            holder.tvPrecio = vistaReusable.findViewById(R.id.tv_precio_comida);
            vistaReusable.setTag(holder);
        } else {
            holder = (ViewHolder) vistaReusable.getTag();
        }

        Comida comida = getItem(posicion);
        holder.tvNombre.setText(comida.getNombre());
        holder.tvPrecio.setText(String.format("$%,.2f", comida.getPrecio()));

        return vistaReusable;
    }

    private static class ViewHolder {
        TextView tvNombre;
        TextView tvPrecio;
    }
}
