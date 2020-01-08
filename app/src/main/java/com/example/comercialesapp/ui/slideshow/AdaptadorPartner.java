package com.example.comercialesapp.ui.slideshow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comercialesapp.R;

public class AdaptadorPartner extends ArrayAdapter{
    private Activity contexto;
    private Partner[] datos;

    public AdaptadorPartner(Activity context, Partner[] datos) {
        super(context, R.layout.listview_partner, datos);
        this.contexto = context;
        this.datos = datos;
    }

    static class ViewHolder{
        protected TextView nombre;
        protected TextView telefono;
        protected TextView correo;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Partner partner;
        partner = datos[position];
        View item = null;

        if (convertView == null) {
            LayoutInflater inflater = contexto.getLayoutInflater();
            item = inflater.inflate(R.layout.listview_partner, null);
            final ViewHolder holder = new ViewHolder();
            holder.nombre = item.findViewById(R.id.txtNombreMostrar);
            holder.telefono = item.findViewById(R.id.txtTelefonomostrar);
            holder.correo = item.findViewById(R.id.txtTelefonomostrar);
            item.setTag(holder);
        } else {
            item = convertView;
        }

        ViewHolder holder = (ViewHolder) item.getTag();
        holder.nombre.setText(partner.getNombre());
        holder.telefono.setText(partner.getTelefono());
        holder.correo.setText(partner.getCorreo());

        return item;
    }
}