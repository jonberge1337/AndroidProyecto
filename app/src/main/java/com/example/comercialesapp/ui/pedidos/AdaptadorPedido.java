package com.example.comercialesapp.ui.pedidos;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comercialesapp.R;

import java.util.ArrayList;

public class AdaptadorPedido extends ArrayAdapter {
    private Context contexto;
    private ArrayList<Pedido> datos;
    private int imagenes[];

    public AdaptadorPedido(Activity context, ArrayList<Pedido> datos, int[] imagenes) {
        super(context, R.layout.listview_articulo, datos);
        this.contexto = context;
        this.datos = datos;
        this.imagenes = imagenes;
    }

    static class ViewHolder {
        private EditText articulo;
        private EditText articuloNombre;
        private EditText cantdad;
        private EditText precio;
        private EditText descuento;
        private ImageView imagen;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pedido pedido;
        pedido = datos.get(position);
        View item;

        if (convertView == null) {
//            LayoutInflater inflater = contexto.getLayoutInflater();
            LayoutInflater inflater = LayoutInflater.from(contexto);
            item = inflater.inflate(R.layout.listview_partner, null);
            final ViewHolder holder = new ViewHolder();
            holder.articulo = item.findViewById(R.id.articuloID);
            holder.articuloNombre = item.findViewById(R.id.txtArticuloCatalogo);
            holder.cantdad = item.findViewById(R.id.txtCantidad);
            holder.precio = item.findViewById(R.id.txtPrecio);
            holder.descuento = item.findViewById(R.id.txtDescuento);
            holder.imagen = item.findViewById(R.id.imgArticulo);
            item.setTag(holder);
        } else {
            item = convertView;
        }

        ViewHolder holder = (ViewHolder) item.getTag();
        holder.articulo.setText(pedido.getArticuloID());
        holder.articuloNombre.setText(pedido.getArticuloNombre());
        holder.cantdad.setText(String.valueOf(pedido.getCantidad()));
        holder.precio.setText(String.valueOf(pedido.getPrecio()));
        holder.descuento.setText(String.valueOf(pedido.getCantidad()));
        holder.imagen.setImageResource(imagenes[position]);

        return item;

    }
}
