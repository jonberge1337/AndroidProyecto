package com.example.comercialesapp.ui.pedidos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comercialesapp.MainActivity;
import com.example.comercialesapp.R;
import com.example.comercialesapp.TablaSQL;

import java.util.ArrayList;
import java.util.Objects;

public class ResumenPedido extends Fragment implements View.OnClickListener {
    private ImageButton botonInicio;
    private ArrayList<Pedido> pedidos;
    private ArrayList<String> partners;
    private ListView listaPartners;
    private Spinner partner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);


        TablaSQL tabla = new TablaSQL(getContext(), "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        String consulta = "SELECT PEDIDO.ARTICULOID AS ID, ARTICULO.DESCRIPCION AS DESCRIPCION," +
                " ARTICULO.PR_VENTA AS PRECIO, ARTICULO.FOTO AS IMAGEN, PEDIDO.CANTIDAD AS CANTIDAD" +
                ", PEDIDO.DESCUENTO AS DESCUENTO, ARTICULO.STOCK AS STOCK" +
                " FROM PEDIDO INNER JOIN ARTICULO ON ARTICULO.ARTICULOID = PEDIDO.ARTICULOID";
        Cursor c = db.rawQuery(consulta, null);

        while (c.moveToNext()){
            String id;
            String descripcion;
            float precio;
            String imagen;
            int descuento;
            int cantidad;
            int stock;

            id = c.getString(c.getColumnIndex("ID"));
            descripcion = c.getString(c.getColumnIndex("DESCRIPCION"));
            precio = c.getFloat(c.getColumnIndex("PRECIO"));
            imagen = c.getString(c.getColumnIndex("IMAGEN"));
            descuento = c.getInt(c.getColumnIndex("DESCUENTO"));
            cantidad = c.getInt(c.getColumnIndex("CANTIDAD"));
            stock = c.getInt(c.getColumnIndex("STOCK"));

            Pedido pedido = new Pedido(id, descripcion, precio, imagen, descuento, cantidad, stock);
            pedidos.add(pedido);
        }

        consulta = "SELECT EMPRESA FROM PARTNER";
        c = db.rawQuery(consulta, null);
        while (c.moveToNext()){
            partners.add(c.getString(c.getColumnIndex("EMPRESA")));
        }

        ArrayAdapter<String> adaptadorPartners = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, partners);
        partner = root.findViewById(R.id.spinnerPartner);
        partner.setAdapter(adaptadorPartners);

        AdaptadorPedido adaptador = new AdaptadorPedido(getActivity(), pedidos, true);
        listaPartners = root.findViewById(R.id.lstPartnersMostrar);
        listaPartners.setAdapter(adaptador);

        this.botonInicio = root.findViewById(R.id.btnResumenPedidoHome);
        this.botonInicio.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intencion = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), MainActivity.class);
        startActivity(intencion);
    }
}