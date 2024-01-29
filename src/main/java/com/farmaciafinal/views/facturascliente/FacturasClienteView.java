package com.farmaciafinal.views.facturascliente;

import com.farmaciafinal.models.EncabezadoFactura;
import com.farmaciafinal.models.Cliente;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

// Define el título de la página y la ruta para esta vista
@PageTitle("Facturas Cliente")
@Route(value = "facturas-cliente", layout = MainLayout.class)
@Uses(Icon.class)
public class FacturasClienteView extends Composite<VerticalLayout> {
    private ComboBox<Cliente> clienteComboBox;
    private Button buscarButton;
    private Grid<EncabezadoFactura> grid;

    // Constructor de la vista
    public FacturasClienteView() {
        // Configuración de diseño y estilo de la vista
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        // Inicializar ComboBox para mostrar clientes
        clienteComboBox = new ComboBox<>("Cliente");
        clienteComboBox.setItems(Utils.listaCliente);
        clienteComboBox.setItemLabelGenerator(Cliente::getNombre); // Cambia el método según el atributo que quieras mostrar

        // Inicializar Button para buscar facturas
        buscarButton = new Button("Buscar");
        buscarButton.addClickListener(e -> buscarFacturas());

        // Inicializar Grid para mostrar las facturas
        grid = new Grid<>(EncabezadoFactura.class, false);
        grid.setColumns("id", "fecha", "cliente.cedula", "producto.nombreProducto", "cantidad", "total");

        // Agregar componentes al diseño
        getContent().add(clienteComboBox, buscarButton, grid);
    }

    // Método para buscar las facturas del cliente seleccionado
    private void buscarFacturas() {
        Cliente clienteSeleccionado = clienteComboBox.getValue();
        if (clienteSeleccionado == null) {
            // Si no se ha seleccionado ningún cliente, no se realiza la búsqueda
            return;
        }

        // Filtrar las facturas por cliente seleccionado
        List<EncabezadoFactura> facturasCliente = Utils.listaEncabezadoFactura.stream()
                .filter(factura -> factura.getCliente().equals(clienteSeleccionado))
                .collect(Collectors.toList());

        // Mostrar las facturas encontradas en el grid
        grid.setItems(facturasCliente);
    }
}
