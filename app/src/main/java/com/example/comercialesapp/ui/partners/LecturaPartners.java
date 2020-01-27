package com.example.comercialesapp.ui.partners;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.comercialesapp.R;
import com.example.comercialesapp.TablaSQL;

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
        ArrayList<Partner> partners = new ArrayList<>();

        TablaSQL tabla = new TablaSQL(getActivity(), "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PARTNER", null);

        String id;
        String nombre;
        String apellido1;
        String apellido2;
        String correo;
        String telefono;

        if (c.moveToFirst()){
            while (c.moveToNext()){

                id = c.getString(c.getColumnIndex("PARTNERID"));
                nombre = c.getString(c.getColumnIndex("NOMBRE"));
                apellido1 = c.getString(c.getColumnIndex("APELLIDO1"));
                apellido2 = c.getString(c.getColumnIndex("APELLIDO2"));
                correo = c.getString(c.getColumnIndex("CORREO"));
                telefono = c.getString(c.getColumnIndex("TELEFONO"));

                Partner partner = new Partner(id,nombre + " " + apellido1 + " " + apellido2, correo, telefono);
                partners.add(partner);
            }
        }
        c.close();

        AdaptadorPartner adaptador = new AdaptadorPartner(getActivity(), partners);
        listaPartners = root.findViewById(R.id.lstPartnersMostrar);
        listaPartners.setAdapter(adaptador);
//        listaPartners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "Hola mundo", Toast.LENGTH_SHORT).show();
//            }
//        });

        nuevoPartner = root.findViewById(R.id.btnAbrirCreacionPartner);
        nuevoPartner.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

//        Toast.makeText(getActivity(), "Hola mudno", Toast.LENGTH_SHORT).show();
        Fragment newFragment = new EscrituraPartners();
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentPartnersID, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
        LinearLayout layout = this.vista.findViewById(R.id.invisibleLayout);
        layout.setVisibility(View.GONE);

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.e("prueba" , "prueba");
//        Toast.makeText(getActivity(), "Hola mundo", Toast.LENGTH_SHORT).show();
//    }
}
