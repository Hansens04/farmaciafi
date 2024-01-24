package com.farmaciafinal.models;
import com.farmaciafinal.models.*;
import java.util.ArrayList;
import java.util.List;

public class Proveedor{
    private String nombreProveedor;
    private String telefonoProveedor;
    private String codigo;
    private String direccion;
    private List<String> producto;
    private List<EncabezadoPedido> encabezadoP;
    private Fecha fecha;

    public Proveedor(String nombreProveedor, String telefonoProveedor,String codigo,String direccion, List<String> producto) {
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.codigo=codigo;
        this.direccion=direccion;
        this.producto = producto;
    }//que permita los cambios de cambiar o eliminar productos.
    //metodo de Agregar productos a la clase y metodo buscar
    //se puede utilizar el remover
    // no se puede eliminar una lista de pedidos o productos
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoP) {
        this.telefonoProveedor = telefonoP;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<String> getProducto() {
        return producto;
    }

    public void setProducto(List<String> producto) {
        this.producto = producto;
    }

    public void modificarProveedor(String pNombreProveedor, String pNuevoTelefono, List<String> pProducto) {
        this.nombreProveedor = pNombreProveedor;
        this.telefonoProveedor = pNuevoTelefono;
        this.producto = pProducto;
    }
    public void agregarProducto(String nuevoProducto) {
        if (producto == null) {
            producto = new ArrayList<>();
        }
        producto.add(nuevoProducto);
    }

    @Override
    public String toString() {
        return "Proveedores" +
                "\nNombreProveedor:" + nombreProveedor +
                "\nTelefono Proveedor:" + telefonoProveedor +
                "\nProducto:" + producto;
    }
    public double calcularValorTotalPedidos() {
        double valorTotal = 0.0;

        for (EncabezadoPedido encabezadoPedido : encabezadoP) {
            for (DetallePedido detallePedido : encabezadoPedido.getDetalleP()) {
                valorTotal += detallePedido.calcularTotal();
            }
        }

        return valorTotal;
    }
}

//consulta cuantos pedidos le he hecho al proveedor, cual es total a pagar de un proveedor, cuantos pedidos se le ha hecho desde cierta fecha...
