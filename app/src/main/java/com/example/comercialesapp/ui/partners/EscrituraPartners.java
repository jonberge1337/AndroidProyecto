package com.example.comercialesapp.ui.partners;

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

import com.example.comercialesapp.R;

import java.util.Objects;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crear_partner, container, false);

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

        Button crearPartner = root.findViewById(R.id.btnPartnerNuevo); // Creamos aquí implícitamente por recomendación de android-studio

        crearPartner.setOnClickListener(this);
        return root;
    }

    public void generarXml(){
        boolean camposVacios = false;
        Log.e("Metodo", "Entra");

        LinearLayout grupo = Objects.requireNonNull(getView()).findViewById(R.id.layoutCreacionPartners);
        for (int i = 0; i < grupo.getChildCount(); i++){
            View texto = grupo.getChildAt(i);
            if (texto instanceof EditText){
                Log.e("", "Es instancia de editText");
                if (texto.toString().length() > 0){
                    camposVacios = true;
                }
            }
        }


        if (!camposVacios){
            Log.e("prueba", empresa.getText().toString());
            ArchivoXML xmlPartner = new ArchivoXML(empresa.getText().toString(), nombre.getText().toString(),
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
        generarXml();
    }
}