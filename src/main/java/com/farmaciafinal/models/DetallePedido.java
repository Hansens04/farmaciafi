package com.farmaciafinal.models;
import com.farmaciafinal.models.*;
public class DetallePedido {
    private Producto productos;
    private EncabezadoPedido encabezadoPedido;
    private double total;
    public DetallePedido(Producto productos, EncabezadoPedido encabezadoPedido){
        this.productos= productos;
        this.encabezadoPedido=encabezadoPedido;
    }

    public DetallePedido(String codigoProducto, String nombreProducto, int cantidad) {
    }

    public Producto darProducto(){
        return productos;
    }
    public double calcularTotal(){
        total=0.0;
        total= darProducto().getPrecio()* darProducto().getStock();
        return total;
    }


}