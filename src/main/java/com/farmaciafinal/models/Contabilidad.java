package com.farmaciafinal.models;
import com.farmaciafinal.models.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class Contabilidad {
    private List<EncabezadoPedido> pedidos;
    private List<EncabezadoFactura> facturas;

    public Contabilidad() {
        this.pedidos = new ArrayList<>();
        this.facturas = new ArrayList<>();
    }

    public void darPedido(EncabezadoPedido pedido) {
        pedidos.add(pedido);
    }

    public void darFactura(EncabezadoFactura factura) {
        facturas.add(factura);
    }





   }