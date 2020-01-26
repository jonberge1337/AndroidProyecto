package com.example.comercialesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.comercialesapp.ui.partners.PartnersXML;

public class TablaSQL extends SQLiteOpenHelper {

//    public TablaSQL(@Nullable PartnersXML context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public TablaSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
                " CORREO TEXT NOT NULL," +
                " PASSWD TEXT NOT NULL," +
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
                "CORREO TEXT," +
                "TELEFONO TEXT," +
                "FORMAPAGOID INTEGER NOT NULL," +
                "COMERCIALID INTEGER NOT NULL," +
                "CONSTRAINT FK_COMERCIAL FOREIGN KEY (COMERCIALID) REFERENCES COMERCIAL(COMERCIALID)" +
                ")";
        db.execSQL(sentenciaCrearTabla);
        sentenciaCrearTabla = "CREATE TABLE CAB_PEDIDO(" +
                "PEDIDOID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "FECHAPEDIDO DATE," +
                "COMERCIALID INTEGER," +
                "PARTNERID INTEGER," +
                "CONSTRAINT FK_COMERCIAL FOREIGN KEY (COMERCIALID) REFERENCES COMERCIAL(COMERCIALID)" +
                ")";
        db.execSQL(sentenciaCrearTabla);
        sentenciaCrearTabla = "CREATE TABLE ARTICULO(" +
                "ARTICULOID INTEGER PRIMARY KEY NOT NULL," +
                "CATEGORIAID INTEGER," +
                "DESCRIPCION TEXT," +
                "PR_VENTA NUMERIC," +
                "PR_COMPRA NUMERIC," +
                "STOCK INTEGER," +
                "STOCK_MINIMO INTEGER," +
                "UNIDADID INTEGER," +
                "FOTO TEXT" +
                ")";
        db.execSQL(sentenciaCrearTabla);
        sentenciaCrearTabla = "CREATE TABLE LINEA(" +
                "PEDIDOID INTEGER NOT NULL," +
                "ARTICULOID INTEGER NOT NULL," +
                "PRECIO NUMERIC," +
                "CANTIDAD INTEGER," +
                "DESCUENTO INTEGER," +
                "CONSTRAINT FK_PEDIDO FOREIGN KEY (ARTICULOID) REFERENCES ARTICULO(ARTICULOID)," +
                "CONSTRAINT FK_PEDIDO1 FOREIGN KEY (PEDIDOID) REFERENCES CAB_PEDIDO(PEDIDOID)," +
                "CONSTRAINT PK_LINEA PRIMARY KEY (PEDIDOID, ARTICULOID)" +
                ")";
        db.execSQL(sentenciaCrearTabla);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
