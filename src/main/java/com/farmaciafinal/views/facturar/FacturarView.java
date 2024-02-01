package com.farmaciafinal.views.facturar;

import com.farmaciafinal.models.Cliente;
import com.farmaciafinal.models.DetalleFactura;
import com.farmaciafinal.models.EncabezadoFactura;
import com.farmaciafinal.models.Producto;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@PageTitle("Facturar")
@Route(value = "facturar", layout = MainLayout.class)
public class FacturarView extends VerticalLayout {

    private ComboBox<Cliente> clienteComboBox = new ComboBox<>("Seleccione un cliente");
    private ComboBox<Producto> productoComboBox = new ComboBox<>("Seleccione un producto");
    private TextField cantidadTextField = new TextField("Cantidad");
    private TextField idFacturaTextField = new TextField("ID de la factura");
    private Button agregarDetalleButton = new Button("Agregar Detalle");
    private Button generarFacturaButton = new Button("Generar Factura");
    private Grid<DetalleFactura> detallesGrid = new Grid<>(DetalleFactura.class, false);

    private EncabezadoFactura encabezadoFactura;

    public FacturarView() {
        clienteComboBox.setItems(Utils.listaCliente);
        clienteComboBox.setItemLabelGenerator(Cliente::getNombre);
        productoComboBox.setItems(Utils.listaProdcuto);
        productoComboBox.setItemLabelGenerator(Producto::getNombreProducto);

        agregarDetalleButton.addClickListener(e -> agregarDetalle());
        generarFacturaButton.addClickListener(e -> generarFactura());

        detallesGrid.addColumn(DetalleFactura::getNombreProducto).setHeader("Producto");
        detallesGrid.addColumn(DetalleFactura::getCantidad).setHeader("Cantidad");
        detallesGrid.addColumn(detalle -> detalle.getProducto().getPrecio() * detalle.getCantidad()).setHeader("Subtotal");

        add(clienteComboBox, productoComboBox, cantidadTextField, idFacturaTextField,
                agregarDetalleButton, generarFacturaButton, detallesGrid);
    }

    private void agregarDetalle() {
        Cliente clienteSeleccionado = clienteComboBox.getValue();
        Producto productoSeleccionado = productoComboBox.getValue();
        String cantidadString = cantidadTextField.getValue();
        String idFactura = idFacturaTextField.getValue();

        if (clienteSeleccionado == null || productoSeleccionado == null || cantidadString.isEmpty() || idFactura.isEmpty()) {
            Notification.show("Por favor, complete todos los campos.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadString);
        } catch (NumberFormatException ex) {
            Notification.show("La cantidad debe ser un número entero.");
            return;
        }

        if (cantidad <= 0) {
            Notification.show("La cantidad debe ser mayor que cero.");
            return;
        }

        if (encabezadoFactura == null) {
            // Crear un nuevo encabezado de factura si no existe
            encabezadoFactura = new EncabezadoFactura(clienteSeleccionado, idFactura);
        }

        DetalleFactura detalle = new DetalleFactura(productoSeleccionado, cantidad);
        encabezadoFactura.agregarDetalle(detalle);
        detallesGrid.setItems(encabezadoFactura.getDetalles());

        // Limpiar campos después de agregar un detalle
        productoComboBox.clear();
        cantidadTextField.clear();
    }

    private void generarFactura() {
        if (encabezadoFactura == null || encabezadoFactura.getDetalles().isEmpty()) {
            Notification.show("No hay detalles para generar la factura.");
            return;
        }

        Cliente cliente = encabezadoFactura.getCliente();
        List<DetalleFactura> detalles = encabezadoFactura.getDetalles();
        double total = detalles.stream().mapToDouble(detalle -> detalle.getProducto().getPrecio() * detalle.getCantidad()).sum();

        StringBuilder facturaTexto = new StringBuilder();
        facturaTexto.append("-------------------------------------Factura----------------------------------------------\n");
        facturaTexto.append("Fecha: ").append(LocalDate.now()).append("\n");
        facturaTexto.append("ID de la factura: ").append(encabezadoFactura.getId()).append("\n");
        facturaTexto.append("Nombre del Cliente: ").append(cliente.getNombre()).append("\n");
        facturaTexto.append("Cédula: ").append(cliente.getCedula()).append("\n");
        facturaTexto.append("Teléfono: ").append(cliente.getTelefono()).append("\n");
        facturaTexto.append("-------------------------------------Detalle del Pedido-----------------------------------\n");

        for (DetalleFactura detalle : detalles) {
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();
            facturaTexto.append("Producto: ").append(producto.getNombreProducto()).append(" - Cantidad: ").append(cantidad).append(" - Precio unitario: ").append(producto.getPrecio()).append(" - Subtotal: ").append(producto.getPrecio() * cantidad).append("\n");
        }

        facturaTexto.append("Total: ").append(total);

        try (FileWriter writer = new FileWriter("factura.txt")) {
            writer.write(facturaTexto.toString());
            Notification.show("Factura generada correctamente en factura.txt");
        } catch (IOException e) {
            Notification.show("Error al generar la factura: " + e.getMessage());
        }

        clienteComboBox.clear();
        productoComboBox.clear();
        cantidadTextField.clear();
        encabezadoFactura = null;
        detallesGrid.setItems(); // Limpiar los detalles de la factura
    }
}