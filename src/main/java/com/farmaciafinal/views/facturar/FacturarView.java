package com.farmaciafinal.views.facturar;

import com.farmaciafinal.models.Cliente;
import com.farmaciafinal.models.EncabezadoFactura;
import com.farmaciafinal.models.Producto;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@PageTitle("Facturar")
@Route(value = "facturar", layout = MainLayout.class)
public class FacturarView extends Composite<VerticalLayout> {
    private VerticalLayout layoutColumn2 = new VerticalLayout();
    private H3 h3 = new H3();
    private FormLayout formLayout2Col = new FormLayout();
    private ComboBox<Cliente> clienteComboBox = new ComboBox<>("Cliente");
    private ComboBox<Producto> productoComboBox = new ComboBox<>("Producto");
    private NumberField cantidadF = new NumberField("Cantidad");
    private TextField idField = new TextField("ID Factura");
    private TextField fechaField = new TextField("Fecha");
    private HorizontalLayout layoutRow = new HorizontalLayout();
    private Button guardar = new Button("Guardar");
    private Button cancelar = new Button("Cancelar");
    private Grid<EncabezadoFactura> grid = new Grid<>(EncabezadoFactura.class, false);
    private H4 masVendido = new H4();
    private H4 menosVendido = new H4();

    private EncabezadoFactura encabezadoFacturaEnEdicion;

    public FacturarView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Factura tu producto");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        clienteComboBox.setItems(Utils.listaCliente);
        clienteComboBox.setItemLabelGenerator(Cliente::getCedula);
        productoComboBox.setItems(Utils.listaProdcuto);
        productoComboBox.setItemLabelGenerator(Producto::getNombreProducto);
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelar.setWidth("min-content");

        // Configurar campos de fecha y ID de factura
        fechaField.setReadOnly(true);
        LocalDate fechaActual = LocalDate.now();
        fechaField.setValue(fechaActual.toString());

        guardar.addClickListener(e -> {
            Cliente clienteSeleccionado = clienteComboBox.getValue();
            Producto productoSeleccionado = productoComboBox.getValue();
            int cantidad = cantidadF.getValue().intValue();

            if (clienteSeleccionado == null || productoSeleccionado == null || cantidad <= 0) {
                Notification.show("Por favor, complete todos los campos correctamente", 3000, Notification.Position.MIDDLE);
                return;
            }

            if (!productoSeleccionado.haySuficienteStock(cantidad)) {
                Notification.show("No hay suficiente stock para el producto seleccionado", 3000, Notification.Position.MIDDLE);
                return;
            }

            double total = calcularTotal(productoSeleccionado.getPrecio(), cantidad);


            if (encabezadoFacturaEnEdicion != null) {
                encabezadoFacturaEnEdicion.actualizar(clienteSeleccionado, productoSeleccionado, cantidad, total, idField.getValue());
            } else {
                EncabezadoFactura encabezadoFactura = new EncabezadoFactura(clienteSeleccionado, productoSeleccionado, cantidad, total, idField.getValue());
                Utils.listaEncabezadoFactura.add(encabezadoFactura);
            }

            productoSeleccionado.actualizarStock(cantidad);
            productoSeleccionado.registrarVenta(cantidad); // Registrar la venta del producto

            actualizarGridYLimpiarCampos();
            actualizarProductosVendidos();

            Notification.show("Factura guardada exitosamente", 3000, Notification.Position.MIDDLE);
        });

        cancelar.addClickListener(e -> {
            clienteComboBox.clear();
            productoComboBox.clear();
            cantidadF.clear();

            // No limpiamos los campos de fecha y ID, ya que son generados automáticamente
        });

        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(clienteComboBox);
        formLayout2Col.add(productoComboBox);
        formLayout2Col.add(cantidadF);
        formLayout2Col.add(idField);
        formLayout2Col.add(fechaField);
        layoutRow.add(cancelar);
        layoutRow.add(guardar);
        layoutColumn2.add(layoutRow);

        grid.addColumn(EncabezadoFactura::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(encabezadoFactura -> encabezadoFactura.getCliente().getCedula()).setHeader("Cedula Cliente").setAutoWidth(true);
        grid.addColumn(encabezadoFactura -> encabezadoFactura.getProducto().getNombreProducto()).setHeader("Producto").setAutoWidth(true);
        grid.addColumn(EncabezadoFactura::getCantidad).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(EncabezadoFactura::getTotal).setHeader("Total").setAutoWidth(true);
        grid.addColumn(EncabezadoFactura::getFecha).setHeader("Fecha").setAutoWidth(true); // Mostrar la fecha en el grid

        grid.addColumn(
                new ComponentRenderer<>(encabezadoFactura -> {
                    Button botonBorrar = new Button(new Icon(VaadinIcon.TRASH));
                    botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    botonBorrar.addClickListener(event -> borrarFactura(encabezadoFactura));

                    Button botonEditar = new Button(new Icon(VaadinIcon.EDIT));
                    botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    botonEditar.addClickListener(event -> editarFactura(encabezadoFactura));

                    HorizontalLayout buttons = new HorizontalLayout(botonBorrar, botonEditar);
                    return buttons;
                })).setHeader("Acciones").setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        layoutColumn2.add(grid);

        // Agregar H4 para mostrar el producto más vendido y menos vendido
        layoutColumn2.add(masVendido);
        layoutColumn2.add(menosVendido);

        getContent().add(layoutColumn2);
        actualizarProductosVendidos();
    }

    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    private void actualizarGridYLimpiarCampos() {
        grid.setItems(Utils.listaEncabezadoFactura);

        clienteComboBox.clear();
        productoComboBox.clear();
        cantidadF.clear();
        // No limpiamos los campos de fecha y ID, ya que son generados automáticamente
    }

    private void editarFactura(EncabezadoFactura encabezadoFactura) {
        encabezadoFacturaEnEdicion = encabezadoFactura;
        guardar.setText("Actualizar");
        clienteComboBox.setValue(encabezadoFactura.getCliente());
        productoComboBox.setValue(encabezadoFactura.getProducto());
        cantidadF.setValue((double) encabezadoFactura.getCantidad());
        idField.setValue(encabezadoFactura.getId());
    }

    private void borrarFactura(EncabezadoFactura encabezadoFactura) {
        Utils.listaEncabezadoFactura.remove(encabezadoFactura);
        grid.setItems(Utils.listaEncabezadoFactura);
        Notification.show("Factura borrada exitosamente", 3000, Notification.Position.MIDDLE);
        actualizarProductosVendidos();
    }

    private void actualizarProductosVendidos() {
        masVendido.setText("Producto más vendido: " + obtenerProductoMasVendido());
        menosVendido.setText("Producto menos vendido: " + obtenerProductoMenosVendido());
    }

    private String obtenerProductoMasVendido() {
        if (Utils.listaEncabezadoFactura.isEmpty()) {
            return "N/A";
        }

        Map<String, Integer> ventasPorProducto = new HashMap<>();
        for (EncabezadoFactura factura : Utils.listaEncabezadoFactura) {
            String nombreProducto = factura.getProducto().getNombreProducto();
            ventasPorProducto.put(nombreProducto, ventasPorProducto.getOrDefault(nombreProducto, 0) + factura.getCantidad());
        }

        // Encontrar el producto con la mayor cantidad de ventas
        return ventasPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    private String obtenerProductoMenosVendido() {
        if (Utils.listaEncabezadoFactura.isEmpty()) {
            return "N/A";
        }

        Map<String, Integer> ventasPorProducto = new HashMap<>();
        for (EncabezadoFactura factura : Utils.listaEncabezadoFactura) {
            String nombreProducto = factura.getProducto().getNombreProducto();
            ventasPorProducto.put(nombreProducto, ventasPorProducto.getOrDefault(nombreProducto, 0) + factura.getCantidad());
        }

        // Encontrar el producto con la menor cantidad de ventas
        return ventasPorProducto.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}