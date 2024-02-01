package com.farmaciafinal.models;

import java.time.LocalDate;

public class EncabezadoFactura {
    //Atributos
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private double total;
    private String id;
    private LocalDate fecha;

    // Constructor
    public EncabezadoFactura(Cliente cliente, Producto producto, int cantidad, double total, String id) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.id = id;
        this.fecha = LocalDate.now(); // Inicializar la fecha con la fecha actual al crear la factura
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Metodo para calcular el total de la factura
    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    //Metodo para modificar a la factura
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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