package com.example.comercialesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UsuariosSQL extends SQLiteOpenHelper {

    public UsuariosSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sentenciaCrearTabla;
        sentenciaCrearTabla = "CREATE TABLE COMERCIAL (" +
                "COMERCIALID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " NOMBRE TEXT," +
                " APELLIDO1 TEXT," +
                " APELLIDO2 TEXT," +
                " DNI TEXT," +
                " TELEFONO TEXT," +
                " DIRECCION TEXT," +
                " CORREO TEXTO NOT NULL," +
                " PASSWD TEXTO NOT NULL," +
                "LOGUEADO BOOLEAN," +
                " CONSTRAINT UK_COMERCIAL_CORREO UNIQUE (CORREO)" +
                ")";
        db.execSQL(sentenciaCrearTabla);
        sentenciaCrearTabla = "CREATE TABLE PARTNER (" +
                "PARTNERID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "EMPRESA TEXT," +
                "NOMBRE TEXT NOT NULL," +
                "APELLIDO1 TEXT," +
                "APELLIDO2 TEXT," +
                "DNI TEXT NOT NULL," +
                "CIUDAD TEXT," +
                "DIRECCION1 TEXT," +
                "DIRECCION2 TEXT," +
                "FORMAPAGOID INTEGER NOT NULL," +
                "COMERCIALID INTEGER NOT NULL," +
                "CONSTRAINT FK_COMERCIAL FOREIGN KEY (COMERCIALID) REFERENCES COMERCIAL(COMERCIALID)" +
                ")";
        db.execSQL(sentenciaCrearTabla);
//        sentenciaCrearTabla = "CREATE TABLE CAB_PEDIDO(" +
//                ")"
//        db.execSQL(sentenciaCrearTabla);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
