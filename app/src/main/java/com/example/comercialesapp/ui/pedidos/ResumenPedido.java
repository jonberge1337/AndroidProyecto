package com.example.comercialesapp.ui.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comercialesapp.MainActivity;
import com.example.comercialesapp.R;

import java.util.Objects;

public class ResumenPedido extends Fragment implements View.OnClickListener {
    private ImageButton botonInicio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);

        ListView lista = root.findViewById(R.id.lstResumenPedido);

        assert getArguments() != null;
        String value = getArguments().getString("pedidoGenerado");
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, android.R.id., value);
        lista.setAdapter(adapter);



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