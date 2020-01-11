package com.example.comercialesapp.ui.partners;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.comercialesapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class LecturaPartners  extends Fragment implements View.OnClickListener {
    private ImageButton nuevoPartner;
    private ListView listaPartners;
    private View vista;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_partners, container, false);
        this.vista = root;
        ArrayList<Partner> partners;

        ArchivoXML xml  = new ArchivoXML();
        xml.generarDOM();
        partners = xml.leerPartner();

        AdaptadorPartner adaptador = new AdaptadorPartner(getActivity(), partners);
        listaPartners = root.findViewById(R.id.lstPartnersMostrar);
        listaPartners.setAdapter(adaptador);

        nuevoPartner = root.findViewById(R.id.btnAbrirCreacionPartner);
        nuevoPartner.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        Fragment newFragment = new EscrituraPartners();
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentPartnersID, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
        LinearLayout layout = this.vista.findViewById(R.id.invisibleLayout);
        layout.setVisibility(View.GONE);
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

    }
}
