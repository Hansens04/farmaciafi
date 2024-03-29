package com.farmaciafinal.models;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EncabezadoPedido {
    //Atributos
    private Producto producto;
    private int cantidad;
    private double total;
    private String id;

    private String estado;
    private Proveedor proveedor;
    private LocalDate fechaEnvio;

    //Constructor
    public EncabezadoPedido(Producto producto, int cantidad, double total, String id, Proveedor proveedor,String estadodelpedido, LocalDate fechaEnvio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.id = id;
        this.proveedor = proveedor;
        this.estado=estadodelpedido;
        this.fechaEnvio = fechaEnvio;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    private List<DetallePedido> detalleP;

    //Metodo para modificar el encabezado del pedido
    public void actualizar(Proveedor proveedor, Producto producto, int cantidad, double total, String id, String estado, LocalDate fechaEnvio) {
        this.proveedor = proveedor; 
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.id = id;
        this.estado = estado;
        this.fechaEnvio=fechaEnvio;

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }



    public Proveedor getProveedores() {
        return proveedor;
    }

    public String getCodigoPedido(){
        return id;
    }



    public List<DetallePedido> getDetalleP(){
        return detalleP;
    }

    //Metodo para consultar el total de pedidos del proveedor
    public static void consultarTotalPedidosProveedor(EncabezadoPedido encabezadoPedido, Proveedor proveedor) {
        System.out.println("Consulta total de pedidos para el proveedor " + proveedor.getNombreProveedor() + ":");

        for (DetallePedido detalle : encabezadoPedido.getDetalleP()) {
            if (encabezadoPedido.getProveedores().equals(proveedor)) {
                System.out.println("Pedido #" + encabezadoPedido.getCodigoPedido() +
                        " - Producto: " + detalle.darProducto().getNombreProducto() +
                        " - Total: " + detalle.calcularTotal());
            }
        }
    }



    public Proveedor getProveedor() {
        return proveedor;
    }

    public Producto getProducto() {
        return producto;
    }
    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

}