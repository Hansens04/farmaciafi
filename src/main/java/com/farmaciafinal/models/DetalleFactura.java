package com.farmaciafinal.models;

public class DetalleFactura {

    // Atributos
    private Producto producto;
    private String nombreProducto; // Nuevo atributo para almacenar el nombre del producto
    private int cantidad;

    // Constructor
    public DetalleFactura() {
    }

    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.nombreProducto = producto.getNombreProducto(); // Establecer el nombre del producto
        this.cantidad = cantidad;
    }

    // Getters y setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Otros métodos, como calcular subtotal, etc.
}
