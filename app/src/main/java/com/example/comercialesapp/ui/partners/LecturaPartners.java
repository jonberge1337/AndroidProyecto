package com.example.comercialesapp.ui.partners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.comercialesapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class LecturaPartners  extends FragmentActivity implements View.OnClickListener {
    private Button nuevoPartner;
    private ListView listaPartners;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_partners, container, false);

        ArrayList<Partner> partners;

        ArchivoXML xml  = new ArchivoXML();
        xml.generarDOM();
        partners = xml.leerPartner();

//        AdaptadorPartner adaptador = new AdaptadorPartner(getActivity(), partners);
        listaPartners = root.findViewById(R.id.lstPartnersMostrar);
//        listaPartners.setAdapter(adaptador);

        nuevoPartner = root.findViewById(R.id.btnAbrirCreacionPartner);
        nuevoPartner.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
//        Intent intento = new Intent(getActivity(), EscrituraPartners.class);
//        startActivity(intento);
    }
}
