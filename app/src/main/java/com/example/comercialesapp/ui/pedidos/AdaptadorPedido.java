package com.example.comercialesapp.ui.pedidos;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comercialesapp.R;
import com.example.comercialesapp.TablaSQL;

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
        private TextView articuloNombre;
        private EditText cantdad;
        private TextView precio;
        private TextView descuento;
        private ImageView imagen;
        private ImageButton ainadir;
        private ImageButton quitar;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Pedido pedido;
        pedido = datos.get(position);
        View item;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(contexto);
            item = inflater.inflate(R.layout.listview_articulo, null);
            final ViewHolder holder = new ViewHolder();
            holder.articulo = item.findViewById(R.id.articuloID);
            holder.articuloNombre = item.findViewById(R.id.txtArticuloCatalogo);
            holder.cantdad = item.findViewById(R.id.txtCantidad);
            holder.precio = item.findViewById(R.id.txtPrecio);
            holder.descuento = item.findViewById(R.id.txtDescuento);
            holder.imagen = item.findViewById(R.id.imgArticulo);
            holder.ainadir = item.findViewById(R.id.btnListviewPedidoAinadir);
            holder.quitar = item.findViewById(R.id.btnListviewPedidoQuitar);
            item.setTag(holder);
        } else {
            item = convertView;
        }

        final ViewHolder holder = (ViewHolder) item.getTag();
        holder.articulo.setText(pedido.getArticuloID());
        holder.articuloNombre.setText(pedido.getArticuloNombre());
        holder.cantdad.setText(String.valueOf(pedido.getCantidad()));
        holder.precio.setText(String.valueOf(pedido.getPrecio()));
        holder.descuento.setText(String.valueOf(pedido.getDescuento()));
        holder.imagen.setImageResource(imagenes[position]);

        TablaSQL tabla = new TablaSQL(getContext(), "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        holder.ainadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = pedido.getArticuloID();
                String precio = holder.precio.getText().toString();
                String cantidad = holder.cantdad.getText().toString();
                String descuento = holder.descuento.getText().toString();

                String consulta = "SELECT * FROM PEDIDO WHERE ARTICULOID = " + id;

                Cursor c = db.rawQuery(consulta, null);
                if (c.moveToFirst()){
                    consulta = "UPDATE PEDIDO SET PRECIO = " + precio + ", CANTIDAD = " + cantidad +
                            ", DESCUENTO = " + descuento + " WHERE ARTICULOID = " + id;

                    db.execSQL(consulta);
                } else {
                    consulta = "INSERT INTO PEDIDO(ARTICULOID, CANTIDAD, DESCUENTO, PRECIO)" +
                            " VALUES(" + id + "," + cantidad + "," + descuento + "," + precio + ")";
                    db.execSQL(consulta);
                }
            }
        });

        holder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = pedido.getArticuloID();
                String precio = holder.precio.getText().toString();
                String cantidad = holder.cantdad.getText().toString();
                String descuento = holder.descuento.getText().toString();

                String consulta = "SELECT * FROM PEDIDO WHERE ARTICULOID = " + id;

                Cursor c = db.rawQuery(consulta, null);
                if (c.moveToFirst()){
                    consulta = "UPDATE PEDIDO SET PRECIO = " + precio + ", CANTIDAD = " + cantidad +
                            ", DESCUENTO = " + descuento + " WHERE ARTICULOID = " + id;

                    db.execSQL(consulta);
                } else {
                    consulta = "DELETE FROM PEDIDO WHERE ARTICULOID = " + id;
                    db.execSQL(consulta);
                }

            }
        });


        return item;

    }
}
