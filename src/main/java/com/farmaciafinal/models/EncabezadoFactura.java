package com.farmaciafinal.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EncabezadoFactura {
    // Atributos
    private Cliente cliente;
    private List<DetalleFactura> detalles; // Lista de detalles de la factura
    private String id;
    private double total;
    private int cantidad;
    private LocalDate fecha;

    // Constructor
    public EncabezadoFactura(Cliente cliente, String id) {
        this.cliente = cliente;
        this.id = id;
        this.detalles = new ArrayList<>();
    }
    public EncabezadoFactura(Cliente cliente, String id, LocalDate fecha) {
        this.cliente = cliente;
        this.id = id;
        this.fecha=fecha;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Método para calcular el total de la factura
    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    // Método para modificar a la factura
    public void actualizar(Cliente cliente, Producto producto, int cantidad, double total, String id) {
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.total = total;
        this.id = id;
    }


    // Getter y setter para cliente
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Getter y setter para total
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
