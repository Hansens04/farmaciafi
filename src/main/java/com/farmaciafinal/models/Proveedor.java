package com.farmaciafinal.models;
import com.farmaciafinal.models.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Proveedor{
    //Atributos
    private String nombreProveedor;
    private String telefonoProveedor;
    private String codigo;
    private String direccion;
    private List<String> listaProductos;
    private List<EncabezadoPedido> encabezadoP;
    private Fecha fecha;

    //Constructor 
    public Proveedor(String nombreProveedor, String telefonoProveedor,String codigo,String direccion, List<String> producto) {
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.codigo=codigo;
        this.direccion=direccion;
        this.listaProductos = producto;
    }


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


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    @Override
    public String toString() {
        return "Proveedores" +
                "\nNombreProveedor:" + nombreProveedor +
                "\nTelefono Proveedor:" + telefonoProveedor +
                "\nProducto:" + listaProductos;
    }


}
