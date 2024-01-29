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
import com.vaadin.flow.component.datepicker.DatePicker;
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

import java.time.LocalDate;
import java.util.Arrays;

@PageTitle("Realizar Pedido")
@Route(value = "realizar-pedido", layout = MainLayout.class)
public class RealizarPedidoView extends Composite<VerticalLayout> {
    // Componentes de la interfaz de usuario
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
    private final DatePicker fechaEnvioPicker = new DatePicker("Fecha de Envío");

    private EncabezadoPedido encabezadoPedidoEnEdicion;

    // Constructor de la vista
    public RealizarPedidoView() {
        // Configuración del diseño y estilo de la vista
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

        fechaEnvioPicker.setWidth("100%");
        formLayout2Col.add(fechaEnvioPicker);

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Establecer la fecha mínima del DatePicker como la fecha actual
        fechaEnvioPicker.setMin(fechaActual);

        guardar.addClickListener(e -> {
            // Método invocado al hacer clic en el botón "Guardar"
            guardarPedido();
        });

        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            // Método invocado al hacer clic en el botón "Cancelar"
            cancelarPedido();
        });

        // Agregar componentes al diseño
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
        grid.addColumn(EncabezadoPedido::getFechaEnvio).setHeader("Fecha de Envío").setAutoWidth(true);
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

    // Método para calcular el total del pedido
    private double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    // Método para guardar un pedido
    private void guardarPedido() {
        // Obtener los valores de los campos de entrada
        Proveedor proveedorSeleccionado = proveedorComboBox.getValue();
        Producto productoSeleccionado = productoComboBox.getValue();
        int cantidad = cantidadF.getValue().intValue();
        String id = idPedido.getValue();
        String estadoPedido = estadoComboBox.getValue();
        LocalDate fechaEnvio = fechaEnvioPicker.getValue();

        // Calcular el total del pedido
        double total = calcularTotal(productoSeleccionado.getPrecio(), cantidad);
        totalField.setValue(total);

        // Crear o actualizar el encabezado del pedido
        if (encabezadoPedidoEnEdicion != null) {
            encabezadoPedidoEnEdicion.actualizar(proveedorSeleccionado, productoSeleccionado, cantidad, total, id, estadoPedido, fechaEnvio);
        } else {
            EncabezadoPedido nuevoEncabezado = new EncabezadoPedido(productoSeleccionado, cantidad, total, id, proveedorSeleccionado, estadoPedido, fechaEnvio);
            Utils.listaEncabezadoPedido.add(nuevoEncabezado);
            encabezadoPedidoEnEdicion = nuevoEncabezado;
        }

        // Actualizar el stock del producto si el estado del pedido es "entregado"
        if (estadoPedido != null && estadoPedido.equals("Entregado")) {
            productoSeleccionado.setStock(productoSeleccionado.getStock() + cantidad);
        }

        // Actualizar el grid y limpiar los campos
        actualizarGridYLimpiarCampos();

        // Mostrar una notificación de éxito
        Notification.show("Pedido guardado exitosamente", 3000, Notification.Position.MIDDLE);
    }

    // Método para cancelar un pedido
    private void cancelarPedido() {
        // Limpiar los campos
        limpiarCampos();

        // Mostrar una notificación de cancelación
        Notification.show("Operación cancelada", 3000, Notification.Position.MIDDLE);
    }

    // Método para editar un pedido
    private void editarPedido(EncabezadoPedido encabezadoPedido) {
        // Establecer el pedido en edición
        encabezadoPedidoEnEdicion = encabezadoPedido;

        // Establecer los valores de los campos de entrada con los valores del pedido seleccionado
        guardar.setText("Actualizar");
        proveedorComboBox.setValue(encabezadoPedido.getProveedor());
        productoComboBox.setValue(encabezadoPedido.getProducto());
        cantidadF.setValue((double) encabezadoPedido.getCantidad());
        totalField.setValue(encabezadoPedido.getTotal());
        idPedido.setValue(encabezadoPedido.getCodigoPedido());
        estadoComboBox.setValue(encabezadoPedido.getEstado());
        fechaEnvioPicker.setValue(encabezadoPedido.getFechaEnvio());
    }

    // Método para borrar un pedido
    private void borrarPedido(EncabezadoPedido encabezadoPedido) {
        // Remover el pedido de la lista
        Utils.listaEncabezadoPedido.remove(encabezadoPedido);

        // Actualizar el grid
        grid.setItems(Utils.listaEncabezadoPedido);

        // Mostrar una notificación de éxito
        Notification.show("Pedido borrado exitosamente", 3000, Notification.Position.MIDDLE);
    }

    // Método para actualizar el grid y limpiar los campos
    private void actualizarGridYLimpiarCampos() {
        // Actualizar el grid
        grid.setItems(Utils.listaEncabezadoPedido);

        // Limpiar los campos
        limpiarCampos();
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        proveedorComboBox.clear();
        productoComboBox.clear();
        cantidadF.clear();
        totalField.clear();
        idPedido.clear();
        estadoComboBox.clear();
        fechaEnvioPicker.clear();
        encabezadoPedidoEnEdicion = null;
        guardar.setText("Guardar");
    }

    // Método para calcular el total de todas las compras
}
