package com.example.comercialesapp.ui.pedidos;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comercialesapp.R;

import java.util.ArrayList;

public class Catalogo extends Fragment implements View.OnClickListener {

    private ListView listaPedidos;
    private View vista;
    private ImageButton generarPedido;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        int imagenes[];
        View root = inflater.inflate(R.layout.fragment_articulo, container, false);

        this.vista = root;
        ArrayList<Pedido> pedidos;

        PedidoXML xml  = new PedidoXML();
        xml.generarDOM();
        pedidos = xml.leerPedido();
        imagenes = new int[pedidos.size()];

        Resources resources = getContext().getResources();

        for (int i = 0; i < pedidos.size(); i++){
            int resourceId = resources.getIdentifier(pedidos.get(i).getImagen(), "drawable",
                    getContext().getPackageName());
            imagenes[i] = resourceId;
        }

        AdaptadorPedido adaptador = new AdaptadorPedido(getActivity(), pedidos, imagenes);
        listaPedidos = root.findViewById(R.id.lstArticulos);
        listaPedidos.setAdapter(adaptador);

        generarPedido = root.findViewById(R.id.btnAbrirCreacionPartner);
        generarPedido.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        
    }
}