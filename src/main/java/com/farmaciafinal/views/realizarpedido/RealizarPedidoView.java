package com.farmaciafinal.views.realizarpedido;

import com.farmaciafinal.models.Proveedor;
import com.farmaciafinal.models.Producto;
import com.farmaciafinal.models.EncabezadoPedido;
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

import java.util.Arrays;

@PageTitle("Realizar Pedido")
@Route(value = "realizar-pedido", layout = MainLayout.class)
public class RealizarPedidoView extends Composite<VerticalLayout> {
    private final VerticalLayout layoutColumn2 = new VerticalLayout();
    private final H3 h3 = new H3("Realizar Pedido");
    private final FormLayout formLayout2Col = new FormLayout();
    private final ComboBox<Proveedor> proveedorComboBox = new ComboBox<>("Proveedor");
    private final ComboBox<Producto> productoComboBox = new ComboBox<>("Producto");
    private final NumberField cantidadF = new NumberField("Cantidad");
    private final TextField idPedido = new TextField("Id Pedido");
    private final NumberField totalField = new NumberField("Total");
    private final HorizontalLayout layoutRow = new HorizontalLayout();
    private final Button guardar = new Button("Guardar");
    private final Button cancelar = new Button("Cancelar");

    private final Grid<EncabezadoPedido> grid = new Grid<>(EncabezadoPedido.class, false);
    private final ComboBox<String> estadoComboBox = new ComboBox<>("Estado del Pedido");

    private EncabezadoPedido encabezadoPedidoEnEdicion;

    public RealizarPedidoView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);

        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");

        h3.setWidth("100%");

        formLayout2Col.setWidth("100%");
        proveedorComboBox.setItems(Utils.listaProveedores);
        proveedorComboBox.setItemLabelGenerator(Proveedor::getNombreProveedor);
        productoComboBox.setItems(Utils.listaProdcuto);
        productoComboBox.setItemLabelGenerator(Producto::getNombreProducto);

        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        estadoComboBox.setItems(Arrays.asList("Enviado", "En Proceso", "Entregado", "Cancelado"));
        estadoComboBox.getStyle().set("margin-top", "-1.5em");

        guardar.addClickListener(e -> {
            Proveedor proveedorSeleccionado = proveedorComboBox.getValue();
            Producto productoSeleccionado = productoComboBox.getValue();
            int cantidad = cantidadF.getValue().intValue();
            String id = idPedido.getValue();
            String estadoPedido = estadoComboBox.getValue();

            // Calcular el total
            double total = calcularTotal(productoSeleccionado.getPrecio(), cantidad);
            totalField.setValue(total);

            // Actualizar encabezado existente o agregar nuevo encabezado a la lista
            if (encabezadoPedidoEnEdicion != null) {
                // Si existe un encabezado en edición, actualiza sus propiedades
                encabezadoPedidoEnEdicion.actualizar(proveedorSeleccionado, productoSeleccionado, cantidad, total, id, estadoPedido);
            } else {
                // Si no existe, agrega un nuevo encabezado a la lista
                EncabezadoPedido nuevoEncabezado = new EncabezadoPedido(proveedorSeleccionado, productoSeleccionado, cantidad, total, id, estadoPedido);
                Utils.listaEncabezadoPedido.add(nuevoEncabezado);
                encabezadoPedidoEnEdicion = nuevoEncabezado;  // Actualiza la referencia al encabezado en edición
            }

            // Luego, llama al método actualizar con los parámetros
            encabezadoPedidoEnEdicion.actualizar(proveedorSeleccionado, productoSeleccionado, cantidad, total, id, estadoPedido);

            // Actualiza el Grid y limpia los campos
            actualizarGridYLimpiarCampos();

            Notification.show("Pedido guardado exitosamente", 3000, Notification.Position.MIDDLE);
        });


        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            limpiarCampos();
            Notification.show("Operación cancelada", 3000, Notification.Position.MIDDLE);
        });

        getContent().add(layoutColumn2);

        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(proveedorComboBox);
        formLayout2Col.add(productoComboBox);
        formLayout2Col.add(cantidadF);
        formLayout2Col.add(idPedido);
        formLayout2Col.add(totalField);
        formLayout2Col.add(estadoComboBox);

        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar);
        layoutRow.add(cancelar);

        grid.addColumn(EncabezadoPedido::getCodigoPedido).setHeader("ID").setAutoWidth(true);
        grid.addColumn(encabezadoPedido -> encabezadoPedido.getProveedor().getNombreProveedor()).setHeader("Proveedor").setAutoWidth(true);
        grid.addColumn(encabezadoPedido -> encabezadoPedido.getProducto().getNombreProducto()).setHeader("Producto").setAutoWidth(true);
        grid.addColumn(EncabezadoPedido::getCantidad).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(EncabezadoPedido::getTotal).setHeader("Total").setAutoWidth(true);
        grid.addColumn(EncabezadoPedido::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(encabezadoPedido -> {
            Button botonBorrar = new Button(new Icon(VaadinIcon.TRASH));
            botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
            botonBorrar.addClickListener(event -> borrarPedido(encabezadoPedido));

            Button botonEditar = new Button(new Icon(VaadinIcon.EDIT));
            botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            botonEditar.addClickListener(event -> editarPedido(encabezadoPedido));

            HorizontalLayout buttons = new HorizontalLayout(botonBorrar, botonEditar);
            return buttons;
        })).setHeader("Acciones").setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        layoutColumn2.add(guardar, grid);

        getContent().add(layoutColumn2);
    }

    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    private void editarPedido(EncabezadoPedido encabezadoPedido) {
        encabezadoPedidoEnEdicion = encabezadoPedido;
        guardar.setText("Actualizar");
        proveedorComboBox.setValue(encabezadoPedido.getProveedor());
        productoComboBox.setValue(encabezadoPedido.getProducto());
        cantidadF.setValue((double) encabezadoPedido.getCantidad());
        totalField.setValue(encabezadoPedido.getTotal());
        idPedido.setValue(encabezadoPedido.getCodigoPedido());
        estadoComboBox.setValue(encabezadoPedido.getEstado());
    }

    private void borrarPedido(EncabezadoPedido encabezadoPedido) {
        Utils.listaEncabezadoPedido.remove(encabezadoPedido);
        grid.setItems(Utils.listaEncabezadoPedido);
        Notification.show("Pedido borrado exitosamente", 3000, Notification.Position.MIDDLE);
    }
    private void actualizarGridYLimpiarCampos() {
        // Actualiza el Grid
        grid.setItems(Utils.listaEncabezadoPedido);

        // Limpiar los campos después de agregar al pedido
        limpiarCampos();
    }


    private void limpiarCampos() {
        proveedorComboBox.clear();
        productoComboBox.clear();
        cantidadF.clear();
        totalField.clear();
        idPedido.clear();
        estadoComboBox.clear();
        encabezadoPedidoEnEdicion = null;
        guardar.setText("Guardar");
    }
}
