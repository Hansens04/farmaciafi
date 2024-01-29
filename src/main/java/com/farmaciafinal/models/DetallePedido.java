package com.farmaciafinal.models;
import com.farmaciafinal.models.*;
public class DetallePedido {
    //Atributos
    private Producto productos;
    private EncabezadoPedido encabezadoPedido;

    //Constructor
    public DetallePedido(Producto productos, EncabezadoPedido encabezadoPedido){
        this.productos= productos;
        this.encabezadoPedido=encabezadoPedido;
    }


    public DetallePedido(String codigoProducto, String nombreProducto, int cantidad) {
    }

    public Producto darProducto(){
        return productos;
    }

    //Metodo para calcular el total
    public double calcularTotal() {
        // Si no hay producto o no hay stock, el total es cero
        if (productos == null || productos.getStock() <= 0) {
            return 0.0;
        }
        // Calcular el total multiplicando el precio por la cantidad en stock
        return productos.getPrecio() * productos.getStock();
    }

}