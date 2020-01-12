package com.example.comercialesapp.ui.pedidos;

public class Pedido {
    private String articuloID;
    private String articuloNombre;
    private float precio;
    private String imagen;
    private int descuento;
    private int cantidad;
    private int stock;

    public Pedido(String articuloID, String articuloNombre, float precio, String imagen, int descuento, int cantidad, int stock) {
        this.articuloID = articuloID;
        this.articuloNombre = articuloNombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descuento = descuento;
        this.cantidad = cantidad;
        this.stock = stock;
    }

    public String getArticuloID() {
        return articuloID;
    }

    public int getStock() {
        return stock;
    }

    public String getArticuloNombre() {
        return articuloNombre;
    }

    public float getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public int getDescuento() {
        return descuento;
    }

    public int getCantidad() {
        return cantidad;
    }
}
