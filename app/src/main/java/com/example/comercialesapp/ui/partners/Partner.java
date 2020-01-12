package com.example.comercialesapp.ui.partners;

public class Partner {
    private String nombre;
    private String correo;
    private String telefono;

    public Partner(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }


    public String getCorreo() {
        return correo;
    }


    public String getTelefono() {
        return telefono;
    }

}
