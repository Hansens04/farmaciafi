package com.farmaciafinal.models;
import java.util.ArrayList;
import java.util.List;
import com.farmaciafinal.models.*;

public class DetalleFactura {

    //Atributos
    private List<Producto> productos;
    private double subtotal;
    private double descuento;
    private double total;

    public List<Producto> getProductos() {
        return productos;
    }

    //Metodo para agregar un producto
    public void agregarProducto(Producto producto, int cantidad) {
        productos.add(producto);
        subtotal += producto.getPrecio() * cantidad;
        calcularDescuento();
        calcularTotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    // Metodo para calcular el descuento de las compras mayor a 15 productos
    private void calcularDescuento() {
        if (productos.size() >= 15) {
            descuento = subtotal * 0.25; // 25% de descuento si hay 15 o más productos
        } else {
            descuento = 0.0;
        }
    }

    //Metodo para calcular total
    private void calcularTotal() {
        total = subtotal - descuento;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "productos=" + productos +
                ", subtotal=" + subtotal +
                ", descuento=" + descuento +
                ", total=" + total +
                '}';
    }
}
