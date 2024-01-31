package com.farmaciafinal.models;

import com.vaadin.flow.component.textfield.NumberField;

public class Producto {
    // Atributos
    private String idProducto;
    private String nombreProducto;
    private int stock;
    private int cantidadMinima;
    private String descripcion;
    private double precio;
    private int ventas;

    // Constructor
    public Producto(String idProducto, String nombreProducto, String descripcion,
                    int stock, int cantidadMinima, double precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.stock = stock;
        this.cantidadMinima = cantidadMinima;
        this.precio = precio;
        this.ventas = 0;
    }

    // Método para registrar una venta del producto
    public void registrarVenta(int cantidadVendida) {
        this.ventas += cantidadVendida;
    }

    // Método para obtener el número de ventas del producto
    public int getVentas() {
        return ventas;
    }

    // Getters y setters
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método para verificar si hay suficiente stock para una cantidad específica
    public boolean haySuficienteStock(int cantidad) {
        return this.stock >= cantidad;
    }

    // Método para actualizar la cantidad en stock después de realizar una venta
    public void actualizarStock(int cantidadVendida) {
        this.stock -= cantidadVendida;
    }

    // Método para saber si se necesita reabastecer el producto
    public boolean necesitaAbastecimiento() {
        return stock < cantidadMinima;
    }

    // Método para modificar los atributos del producto
    public void modificarProducto(String idProducto, String nombreProducto, String descripcion,
                                  int stock, int cantidadMinima, double precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.stock = stock;
        this.cantidadMinima = cantidadMinima;
        this.precio = precio;
    }
}
