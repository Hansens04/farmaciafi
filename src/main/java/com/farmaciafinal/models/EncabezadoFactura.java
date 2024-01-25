package com.farmaciafinal.models;

public class EncabezadoFactura {
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private double total;
    private String id;

    // Constructor
    public EncabezadoFactura(Cliente cliente, Producto producto, int cantidad, double total,String id) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.id=id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }
    public void actualizar(Cliente cliente, Producto producto, int cantidad, double total,String id) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.id=id;
    }


    // Getter y setter para cliente
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Getter y setter para producto
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Getter y setter para cantidad
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter y setter para total
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
