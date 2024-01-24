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

@PageTitle("Facturar")
@Route(value = "facturar", layout = MainLayout.class)
public class FacturarView extends Composite<VerticalLayout> {
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    FormLayout formLayout2Col = new FormLayout();
    ComboBox<Cliente> clienteComboBox = new ComboBox<>("Cliente");
    ComboBox<Producto> productoComboBox = new ComboBox<>("Producto");
    NumberField cantidadF = new NumberField("Cantidad");
    TextField id = new TextField("Id factura");
    NumberField totalField = new NumberField("Total");
    HorizontalLayout layoutRow = new HorizontalLayout();
    Button guardar = new Button("Guardar");
    Button cancelar = new Button("Cancelar");

    Grid<EncabezadoFactura> grid = new Grid<>(EncabezadoFactura.class, false);

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

        guardar.addClickListener(e -> {
            Cliente clienteSeleccionado = clienteComboBox.getValue();
            Producto productoSeleccionado = productoComboBox.getValue();
            int cantidad = cantidadF.getValue().intValue();

            if (clienteSeleccionado == null || productoSeleccionado == null || cantidad <= 0) {
                Notification.show("Por favor, complete todos los campos correctamente", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Calcular el total
            double total = calcularTotal(productoSeleccionado.getPrecio(), cantidad);
            totalField.setValue(total);

            if (encabezadoFacturaEnEdicion != null) {
                // Si hay un encabezado en edición, actualiza sus valores
                encabezadoFacturaEnEdicion.actualizar(clienteSeleccionado, productoSeleccionado, cantidad, total, id.getValue());

                // Limpia el estado de edición y actualiza el botón de guardar
                encabezadoFacturaEnEdicion = null;
                guardar.setText("Guardar");
            } else {
                // Si no hay encabezado en edición, crea uno nuevo y agrega a la lista
                EncabezadoFactura encabezadoFactura = new EncabezadoFactura(clienteSeleccionado, productoSeleccionado, cantidad, total, id.getValue());
                Utils.listaEncabezadoFactura.add(encabezadoFactura);
            }

            // Actualiza el Grid
            grid.setItems(Utils.listaEncabezadoFactura);

            // Limpiar los campos después de agregar a la factura
            clienteComboBox.clear();
            productoComboBox.clear();
            cantidadF.clear();
            totalField.clear();
            id.clear();

            Notification.show("Factura guardada exitosamente", 3000, Notification.Position.MIDDLE);
        });

        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            // Borrar todos los datos de los ComboBox y NumberField
            clienteComboBox.clear();
            productoComboBox.clear();
            cantidadF.clear();
            totalField.clear();
            id.clear();

            Notification.show("Operación cancelada", 3000, Notification.Position.MIDDLE);
        });

        getContent().add(layoutColumn2);

        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(clienteComboBox);
        formLayout2Col.add(productoComboBox);
        formLayout2Col.add(cantidadF);
        formLayout2Col.add(id);
        formLayout2Col.add(totalField);
        layoutRow.add(cancelar);
        layoutRow.add(guardar); // Cambio en el orden de añadir botones
        layoutColumn2.add(layoutRow);

        // Configura el Grid para mostrar los encabezados de factura
        grid.addColumn(EncabezadoFactura::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(encabezadoFactura -> encabezadoFactura.getCliente().getCedula()).setHeader("Cedula Cliente").setAutoWidth(true);
        grid.addColumn(encabezadoFactura -> encabezadoFactura.getProducto().getNombreProducto()).setHeader("Producto").setAutoWidth(true);
        grid.addColumn(EncabezadoFactura::getCantidad).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(EncabezadoFactura::getTotal).setHeader("Total").setAutoWidth(true);
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

        getContent().add(layoutColumn2);
    }

    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    private void editarFactura(EncabezadoFactura encabezadoFactura) {
        encabezadoFacturaEnEdicion = encabezadoFactura;
        guardar.setText("Actualizar");
        clienteComboBox.setValue(encabezadoFactura.getCliente());
        productoComboBox.setValue(encabezadoFactura.getProducto());
        cantidadF.setValue((double) encabezadoFactura.getCantidad());
        totalField.setValue(encabezadoFactura.getTotal());
        id.setValue(encabezadoFactura.getId());
    }

    private void borrarFactura(EncabezadoFactura encabezadoFactura) {
        Utils.listaEncabezadoFactura.remove(encabezadoFactura);
        grid.setItems(Utils.listaEncabezadoFactura);
        Notification.show("Factura borrada exitosamente", 3000, Notification.Position.MIDDLE);
    }
}
