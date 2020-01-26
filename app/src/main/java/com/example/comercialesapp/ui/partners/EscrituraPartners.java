package com.example.comercialesapp.ui.partners;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.comercialesapp.MainActivity;
import com.example.comercialesapp.R;
import com.example.comercialesapp.TablaSQL;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EscrituraPartners extends Fragment implements View.OnClickListener {
    private EditText empresa;
    private EditText nombre;
    private EditText apellido1;
    private EditText apellido2;
    private EditText dni;
    private EditText ciudad;
    private EditText direccion1;
    private EditText direccion2;
    private EditText formapagoID;
    private EditText correo;
    private EditText telefono;
    private View vista;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crear_partner, container, false);
        this.vista = root;

        empresa = root.findViewById(R.id.txtEmpresaNuevo);
        nombre = root.findViewById(R.id.txtNombreNuevo);
        apellido1 = root.findViewById(R.id.txtApellido1Nuevo);
        apellido2 = root.findViewById(R.id.txtApellido2Nuevo);
        dni = root.findViewById(R.id.txtDNINuevo);
        ciudad = root.findViewById(R.id.txtCiudadNuevo);
        direccion1 = root.findViewById(R.id.txtDireccion1Nuevo);
        direccion2 = root.findViewById(R.id.txtDireccion2Nuevo);
        formapagoID = root.findViewById(R.id.txtFPNuevo);
        correo = root.findViewById(R.id.txtCorreo);
        telefono = root.findViewById(R.id.txtTelefono);

        Button crearPartner = root.findViewById(R.id.btnPartnerNuevo);

        dni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Pattern expresion = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]$");
                    Matcher correcto = expresion.matcher(dni.getText().toString());
                    if (!correcto.find()){
                        Toast.makeText(getActivity(), "No has introducido un DNI correcto", Toast.LENGTH_SHORT).show();
                        dni.setText("");
                    }
                }
            }
        });

        correo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Pattern expresion = Pattern.compile("^.+@.+[.]{1}[a-z]{2,3}");
                    Matcher correcto = expresion.matcher(correo.getText().toString());
                    if (!correcto.find()){
                        Toast.makeText(getActivity(), "No es un correo valido", Toast.LENGTH_SHORT).show();
                        correo.setText("");
                    }
                }
            }
        });

        telefono.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Pattern expresion = Pattern.compile("^[679]{1}[0-9]{8}$");
                    Matcher correcto = expresion.matcher(telefono.getText().toString());
                    if (!correcto.find()){
                        Toast.makeText(getActivity(), "No es un telefono valido", Toast.LENGTH_SHORT).show();
                        telefono.setText("");
                    }
                }
            }
        });

        crearPartner.setOnClickListener(this);
        return root;
    }

    public void generarPartner(){
        boolean camposVacios = false;

        if (!camposVacios){
            Log.e("prueba", empresa.getText().toString());

            TablaSQL tabla = new TablaSQL(getActivity(), "DBUsuarios", null, 1);
            final SQLiteDatabase db = tabla.getWritableDatabase();


            String consulta = "SELECT * FROM PARTNER WHERE DNI = '" + dni.getText().toString() + "'";
            Cursor c = db.rawQuery(consulta, null);

            if (c.moveToFirst()){
                consulta = "UPDATE PARTNER SET EMPRESA = '" + c.getString(c.getColumnIndex("EMPRESA")) + "'," +
                    "NOMBRE = '" + c.getString(c.getColumnIndex("NOMBRE")) + "'," +
                    "APELLIDO1 = '" + c.getString(c.getColumnIndex("APELLIDO1")) + "'," +
                    "APELLIDO2 = '" + c.getString(c.getColumnIndex("APELLIDO2")) + "'," +
                    "CIUDAD = '" + c.getString(c.getColumnIndex("CIUDAD")) + "'," +
                    "DIRECCION1 = '" + c.getString(c.getColumnIndex("DIRECCION1")) + "'," +
                    "DIRECCION2 = '" + c.getString(c.getColumnIndex("DIRECCION2")) + "'," +
                    "FORMAPAGOID = '" + c.getString(c.getColumnIndex("FORMAPAGOID")) + "'" +
                    "WHERE PARTNERID = " + c.getString(c.getColumnIndex("PARTNERID"));
                db.execSQL(consulta);
            } else {
                String comercial;
                consulta = "SELECT COMERCIALID FROM COMERCIAL WHERE LOGUEADO = 1";
                Cursor cursorComercial = db.rawQuery(consulta, null);
                cursorComercial.moveToFirst();
                comercial = cursorComercial.getString(cursorComercial.getColumnIndex("COMERCIALID"));
                cursorComercial.close();

                consulta = "INSERT INTO PARTNER(EMPRESA, NOMBRE, APELLIDO1, APELLIDO2, DNI, CIUDAD, DIRECCION1, DIRECCION2, FORMAPAGOID, CORREO, TELEFONO, COMERCIALID)" +
                        " VALUES ('" + empresa.getText().toString() + "'," +
                        "'" + nombre.getText().toString() + "', " +
                        "'" + apellido1.getText().toString() + "'," +
                        " '" + apellido2.getText().toString() + "'," +
                        " '" + dni.getText().toString() + "'," +
                        " '" + ciudad.getText().toString() + "'," +
                        " '" + direccion1.getText().toString() + "'," +
                        " '" + direccion2.getText().toString() + "'," +
                        " '" + formapagoID.getText().toString() + "'," +
                        " '" + correo.getText().toString() + "'," +
                        " '" + telefono.getText().toString() + "'," +
                        "" + comercial + ")";

                db.execSQL(consulta);
            }
            c.close();
            PartnersXML xmlPartner = new PartnersXML(empresa.getText().toString(), nombre.getText().toString(),
                    apellido1.getText().toString(), apellido2.getText().toString(), dni.getText().toString(),
                    ciudad.getText().toString(), direccion1.getText().toString(), direccion2.getText().toString(),
                    formapagoID.getText().toString(), correo.getText().toString(), telefono.getText().toString() , "1"); // De momento como no tenemos usuarios vamos a dejar el uno en la fase 2 eso hay que cambiar
            xmlPartner.generarDOM();
            xmlPartner.generarDocument();
            xmlPartner.generarXml();
        } else {
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "No has rellenado todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        generarPartner();
        if(empresa.length() > 0 && nombre.length() > 0 && apellido2.length() > 0 && apellido1.length() > 0 && dni.length() > 0 && correo.length() > 0 && telefono.length() > 0) {
//      getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            Fragment newFragment = new LecturaPartners();
            FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.layoutCreacionPartnersID, newFragment);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();
            LinearLayout layout = this.vista.findViewById(R.id.layoutCreacionPartnersID);
            layout.setVisibility(View.GONE);

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            Intent intencion = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            startActivity(intencion);
            Toast.makeText(getActivity().getApplicationContext(), "El partner ha sido añadido al fichero", Toast.LENGTH_SHORT).show();
//        LinearLayout layout2 = this.vista.findViewById(R.id.fragmentPartnersID);

//        layout2.setVisibility(View.VISIBLE);
//        Objects.requireNonNull(getView()).findViewById(R.id.invisibleLayout).setVisibility(View.VISIBLE);
//        String frase = newFragment.getView().toString();
//        Toast.makeText(getActivity().getApplicationContext(), frase, Toast.LENGTH_SHORT).show();
//        newFragment.getView().findViewById(R.id.invisibleLayout).setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "Tienes que rellenar todos los campos obligatorios", Toast.LENGTH_SHORT).show();
        }

    }
}