package com.farmaciafinal.models;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    //Atributos de clase
    private String nombre;
    private String cedula;
    private String telefono;
    private String direccion;

    //Almecenar dato para encabezado de factura
    private final List<EncabezadoFactura> encabezadoFacturas = new ArrayList<>();

    //Constructor
    public Cliente(String nombre, String cedula, String telefono, String direccion) {
        validarDatosEntrada(nombre, cedula, telefono);
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
    }



    //Validación de datos que no sean nulos o vacios
    private void validarDatosEntrada(String nombre, String cedula, String telefono) {
        if (nombre == null || nombre.isEmpty() || cedula == null || cedula.isEmpty() || telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("Los datos de entrada no pueden ser nulos o vacíos.");
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
