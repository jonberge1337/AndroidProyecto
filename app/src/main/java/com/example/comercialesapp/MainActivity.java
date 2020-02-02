package com.example.comercialesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.example.comercialesapp.ui.pedidos.Pedido;
import com.example.comercialesapp.ui.pedidos.PedidoXML;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Environment;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activarCorreo();
            }
        });
        crearArticulos();
        vaciarTablaPedido();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void crearArticulos(){

        final ArrayList<Pedido> pedidos;

        PedidoXML xml  = new PedidoXML(this);
        xml.generarDOM();
        pedidos = xml.leerCatalogo();


        TablaSQL tabla = new TablaSQL(this, "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        String consulta;
        Cursor c;
        consulta = "DELETE FROM ARTICULO";
        db.execSQL(consulta);

        String id;
        String descripcion;
        float prVenta;
        String foto;

        for (Pedido articulo: pedidos){

            id = articulo.getArticuloID();
            descripcion = articulo.getArticuloNombre();
            prVenta = articulo.getPrecio();
            foto = articulo.getImagen();

            consulta = "INSERT INTO ARTICULO(ARTICULOID, DESCRIPCION, PR_VENTA, FOTO) VALUES (" + id + ",'" + descripcion +
                    "'," + prVenta + ",'" + foto + "')";
            db.execSQL(consulta);

        }
    }

    public void activarCorreo() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"delegacionChargenetic@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Xml Partners");
        startActivity(emailIntent);
    }

    public void vaciarTablaPedido(){
        TablaSQL tabla = new TablaSQL(this, "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        String consulta = "DELETE FROM PEDIDO";
        db.execSQL(consulta);

        db.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        TablaSQL tabla = new TablaSQL(this, "DBUsuarios", null, 1);
        final SQLiteDatabase db = tabla.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM COMERCIAL WHERE LOGUEADO = 1", null);

        if(c.moveToFirst()){

            db.execSQL("UPDATE COMERCIAL SET LOGUEADO = 0 WHERE COMERCIALID = " + c.getString(c.getColumnIndex("COMERCIALID")));

        }

        c.close();
        db.close();
    }
}
