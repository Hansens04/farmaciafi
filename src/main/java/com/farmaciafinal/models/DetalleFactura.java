package com.farmaciafinal.models;

import java.util.ArrayList;
import java.util.List;

public class DetalleFactura {

    //Atributos
    private List<Producto> productos;

    // Constructor
    public DetalleFactura() {
        this.productos = new ArrayList<>();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (Producto producto : productos) {
            subtotal += producto.getPrecio();
        }
        return subtotal;
    }

    // Metodo para calcular el descuento de las compras mayor a 15 productos
    public double calcularDescuento() {
        double descuento = 0.0;
        if (productos != null && productos.size() >= 15) {
            descuento = calcularSubtotal() * 0.25; // 25% de descuento si hay 15 o más productos
        }
        return descuento;
    }

    //Metodo para calcular total
    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    //Metodo para agregar un producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "productos=" + productos +
                '}';
    }
}
