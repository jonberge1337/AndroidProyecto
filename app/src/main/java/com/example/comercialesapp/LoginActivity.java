package com.example.comercialesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button botonLogin;
    private EditText usuario;
    private EditText passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TablaSQL tabla = new TablaSQL(this, "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        usuario = findViewById(R.id.txtUsername);
        passwd = findViewById(R.id.txtPassword);
        botonLogin = findViewById(R.id.btnlogin);
        if (db != null){
            final String usuarios[] = {
                    "INSERT INTO COMERCIAL(NOMBRE, CORREO, PASSWD) SELECT 'PATXI', 'patxi1337@cebanc.com', 'CEBANC1819' WHERE NOT EXISTS(SELECT * FROM COMERCIAL WHERE CORREO = 'patxi1337@cebanc.com')",
                    "INSERT INTO COMERCIAL(NOMBRE, CORREO, PASSWD) SELECT 'EDURNE', 'edurne1337@cebanc.com', 'CEBANC1819' WHERE NOT EXISTS(SELECT * FROM COMERCIAL WHERE CORREO = 'edurne1337@cebanc.com')",
                    "INSERT INTO COMERCIAL(NOMBRE, CORREO, PASSWD) SELECT 'JAVI', 'javi1337@cebanc.com', 'CEBANC1819' WHERE NOT EXISTS(SELECT * FROM COMERCIAL WHERE CORREO = 'javi1337@cebanc.com')"
            };
            for (String insert : usuarios) {
                db.execSQL(insert);
            }
            botonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (usuario.length() > 0 && passwd.length() > 0){
                        String consulta = "SELECT * FROM COMERCIAL WHERE CORREO = '" + usuario.getText().toString() + "' AND PASSWD = '" + passwd.getText().toString() + "'";
                        Cursor c;
                        c = db.rawQuery(consulta, null);
                        if (c.moveToFirst()){
                            db.execSQL("UPDATE COMERCIAL SET LOGUEADO = 1 WHERE CORREO = '" + usuario.getText().toString() + "'");
                            Intent intento = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intento);
                            db.close();
//                            Log.e("prueba", "prueba");
//                            Intent intento2 = new Intent(LoginActivity.this, LoginActivity.class);
//                            startActivity(intento2);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "No existe ese usuario con esa contraseña", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Rellena los campos", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
}
