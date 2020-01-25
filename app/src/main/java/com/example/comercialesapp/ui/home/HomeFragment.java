package com.example.comercialesapp.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comercialesapp.R;
import com.example.comercialesapp.TablaSQL;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView titulo = root.findViewById(R.id.lblTitulo_home);

        TablaSQL tabla = new TablaSQL(getActivity(), "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT NOMBRE FROM COMERCIAL WHERE LOGUEADO = 1", null);

        if (c.moveToFirst()){

            titulo.setText(titulo.getText().toString() + " " + c.getString(0));

        }

        return root;
    }
}