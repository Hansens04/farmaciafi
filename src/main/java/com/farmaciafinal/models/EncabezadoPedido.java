package com.farmaciafinal.models;
import java.util.Arrays;
import java.util.List;

public class EncabezadoPedido {
    private Producto producto;
    private int cantidad;
    private double total;
    private String id;
    private Proveedor proveedores;

    private String estado;
    private Proveedor proveedor;

    public EncabezadoPedido(Proveedor proveedorSeleccionado, Producto productoSeleccionado, int cantidad, double total, String value, String estadoPedido) {
    }

    // ... (resto de la clase)

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
    public EncabezadoPedido() {
        this.proveedor = new Proveedor("", "", "", "", List.of(""));
        this.producto = new Producto("",",","",2,2,2);
        this.cantidad = 0;
        this.total = 0.0;
        this.id = "";
        this.estado = "";
    }
    public void actualizar(Proveedor proveedor, Producto producto, int cantidad, double total, String id, String estado) {
        this.proveedor = proveedor; // Update this line
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.id = id;
        this.estado = estado;
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

    public void setTotal(double total) {
        this.total = total;
    }

    public Proveedor getProveedores() {
        return proveedores;
    }

    public String getCodigoPedido(){
        return id;
    }
    public String estadoPedido(){
        return estado;
    }
    public void actualizarEstadoEnviado(String estadoPedido){
        this.estado=estadoPedido;
    }


    public void modificarEncabezado(Proveedor pProveedor, Fecha pFecha){
        this.proveedores= pProveedor;
    }
    public List<DetallePedido> getDetalleP(){
        return detalleP;
    }


    public static void consultarTotalPedidosProveedor(EncabezadoPedido encabezadoPedido, Proveedor proveedor) {
        System.out.println("Consulta total de pedidos para el proveedor " + proveedor.getNombreProveedor() + ":");

        for (DetallePedido detalle : encabezadoPedido.getDetalleP()) {
            if (encabezadoPedido.getProveedores().equals(proveedor)) {
                System.out.println("Pedido #" + encabezadoPedido.getCodigoPedido() +
                        " - Producto: " + detalle.darProducto().getNombreProducto() +
                        " - Total: " + detalle.calcularTotal());
                // Agrega más información según sea necesario
            }
        }
    }

    public void modificarEncabezado(int dia, int mes, int anio, String nombreProveedor,
                                    String telefono, String codigoProducto,String codigoProveedor,String direccion){
        this.proveedores = new Proveedor(nombreProveedor, telefono,codigoProveedor,direccion, Arrays.asList(codigoProducto));
    }


    public Proveedor getProveedor() {
        return proveedor;
    }

    public Producto getProducto() {
        return producto;
    }
}