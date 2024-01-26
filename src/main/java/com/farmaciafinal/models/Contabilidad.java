package com.farmaciafinal.models;

import com.farmaciafinal.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Contabilidad {
    //atributos
    private List<EncabezadoPedido> pedidos;
    private List<EncabezadoFactura> facturas;

    //Constructor
    public Contabilidad() {
        this.pedidos = Utils.listaEncabezadoPedido;
        this.facturas = Utils.listaEncabezadoFactura;
    }
    
    public void darPedido(EncabezadoPedido pedido) {
        pedidos.add(pedido);
    }

    public void darFactura(EncabezadoFactura factura) {
        facturas.add(factura);
    }

    //Metodo para calcular el total de las ventas de la Farmacia
    public double calcularTotalVentas() {
        double totalVentas = 0.0;
        for (EncabezadoFactura factura : facturas) {
            totalVentas += factura.getTotal();
        }
        return totalVentas;
    }

    //Metodo para calcular el total de las compras realizada a los proveedores
    public double calcularTotalCompras() {
        double totalCompras = 0.0;
        for (EncabezadoPedido pedido : pedidos) {
            totalCompras += pedido.getTotal();
        }
        return totalCompras;
    }
}
