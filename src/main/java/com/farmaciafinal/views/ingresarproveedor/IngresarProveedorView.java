package com.farmaciafinal.views.ingresarproveedor;

import com.farmaciafinal.models.Proveedor;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.farmaciafinal.utils.Utils.listaProveedores;

@PageTitle("Ingresar Proveedor")
@Route(value = "ingresar-proveedor", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class IngresarProveedorView extends VerticalLayout {

    // Declaración de componentes de la interfaz de usuario
    private final H3 h3 = new H3("Información del Proveedor");
    private final FormLayout formLayout = new FormLayout();
    private final TextField nombreProveedor = new TextField("Nombre Proveedor");
    private final TextField telefono = new TextField("Teléfono Proveedor");
    private final TextField direccion = new TextField("Dirección");
    private final TextField codigo = new TextField("Código");
    private final TextField productoTextField = new TextField("Producto");
    private final HorizontalLayout buttonsLayout = new HorizontalLayout();
    private final Button guardar = new Button("Guardar");
    private final Button cancelar = new Button("Cancelar");
    private final Button agregarProductoButton = new Button("Agregar Producto");
    private final Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);

    private Proveedor proveedorEditar;
    private boolean modoEdicion = false;

    public IngresarProveedorView() {
        // Configuración de diseño y estilo de la vista
        setWidthFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.addClassName("centered-content");

        h3.setWidth("100%");
        formLayout.setWidth("100%");
        nombreProveedor.setRequired(true);
        telefono.setRequired(true);
        direccion.setRequired(true);
        codigo.setRequired(true);
        productoTextField.setWidthFull();
        buttonsLayout.addClassName("gap-m");
        buttonsLayout.setWidth("100%");

        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addClickListener(e -> {
            if (modoEdicion) {
                guardarEdicion();
            } else {
                guardarNuevoProveedor();
            }
        });

        cancelar.addClickListener(e -> {
            limpiarCampos();
            if (modoEdicion) {
                modoEdicion = false;
                cancelarEdicion();
            }
        });

        agregarProductoButton.addClickListener(e -> agregarProducto());

        // Agregar componentes al layout
        add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout);
        formLayout.add(nombreProveedor, telefono, codigo, direccion, productoTextField);
        layoutColumn2.add(buttonsLayout);
        buttonsLayout.add(guardar, cancelar, agregarProductoButton);

        // Configuración de columnas en el grid de proveedores
        grid.addColumn(Proveedor::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Proveedor::getNombreProveedor).setHeader("Nombre Proveedor").setAutoWidth(true);
        grid.addColumn(Proveedor::getTelefonoProveedor).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Proveedor::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(proveedor -> String.join(", ", proveedor.getListaProductos())).setHeader("Productos").setKey("productos").setAutoWidth(true);
        grid.addComponentColumn(proveedor -> createManageButtons(proveedor)).setHeader("Manage").setAutoWidth(true);

        // Cargar proveedores
        grid.setItems(listaProveedores);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // Agregar el grid al layout
        layoutColumn2.add(grid);
    }

    private HorizontalLayout createManageButtons(Proveedor proveedor) {
        Button editButton = new Button("Editar");
        editButton.addClickListener(event -> editarProveedor(proveedor));

        Button deleteButton = new Button("Eliminar");
        deleteButton.addClickListener(event -> {
            listaProveedores.remove(proveedor);
            grid.getDataProvider().refreshAll();
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(editButton, deleteButton);
        buttonsLayout.setSpacing(true);
        return buttonsLayout;
    }

    private void guardarNuevoProveedor() {
        String nombreProve = nombreProveedor.getValue();
        String telefonoP = telefono.getValue();
        String direccionProveedor = direccion.getValue();
        String codigoProveedor = codigo.getValue();

        if (nombreProve.isEmpty() || telefonoP.isEmpty() || direccionProveedor.isEmpty() || codigoProveedor.isEmpty()) {
            Notification.show("Error: Todos los campos son obligatorios.");
            return;
        }

        if (codigoProveedorExistente(codigoProveedor)) {
            Notification.show("Error: El código del proveedor ya existe.");
            return;
        }

        if (!telefonoP.matches("^09\\d{8}$")) {
            Notification.show("Error: El teléfono debe comenzar con '09' y tener 10 dígitos.");
            return;
        }

        if (!esNumero(codigoProveedor)) {
            Notification.show("Error: El código debe contener solo números.");
            return;
        }

        if (!contieneSoloLetras(nombreProve)) {
            Notification.show("Error: El nombre del proveedor debe contener solo letras.");
            return;
        }

        Proveedor proveedor = new Proveedor(nombreProve, telefonoP, codigoProveedor, direccionProveedor, new ArrayList<>());
        listaProveedores.add(proveedor);

        grid.setItems(listaProveedores);

        limpiarCampos();
    }

    private boolean codigoProveedorExistente(String codigoProveedor) {
        return listaProveedores.stream().anyMatch(proveedor -> proveedor.getCodigo().equals(codigoProveedor));
    }

    private boolean esNumero(String cadena) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    private boolean contieneSoloLetras(String cadena) {
        Pattern pattern = Pattern.compile("[a-zA-Z ]+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    private void editarProveedor(Proveedor proveedor) {
        proveedorEditar = proveedor;
        modoEdicion = true;

        nombreProveedor.setValue(proveedor.getNombreProveedor());
        telefono.setValue(proveedor.getTelefonoProveedor());
        direccion.setValue(proveedor.getDireccion());
        codigo.setValue(proveedor.getCodigo());

        guardar.setText("Actualizar");
        guardar.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        buttonsLayout.remove(cancelar);
    }

    private void guardarEdicion() {
        String nombreProve = nombreProveedor.getValue();
        String telefonoP = telefono.getValue();
        String direccionProveedor = direccion.getValue();

        if (nombreProve.isEmpty() || telefonoP.isEmpty() || direccionProveedor.isEmpty()) {
            Notification.show("Error: Todos los campos son obligatorios.");
            return;
        }

        proveedorEditar.setNombreProveedor(nombreProve);
        proveedorEditar.setTelefonoProveedor(telefonoP);
        proveedorEditar.setDireccion(direccionProveedor);

        listaProveedores.remove(proveedorEditar);
        listaProveedores.add(proveedorEditar);

        grid.setItems(listaProveedores);

        limpiarCampos();
        modoEdicion = false;
        guardar.setText("Guardar");
        guardar.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonsLayout.add(cancelar);
    }

    private void cancelarEdicion() {
        limpiarCampos();
        modoEdicion = false;
        guardar.setText("Guardar");
        guardar.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonsLayout.add(cancelar);
    }

    private void limpiarCampos() {
        nombreProveedor.clear();
        telefono.clear();
        direccion.clear();
        codigo.clear();
    }

    private void agregarProducto() {
        String nombreProducto = productoTextField.getValue();

        if (nombreProducto.isEmpty()) {
            Notification.show("Error: Por favor ingrese el nombre del producto.");
            return;
        }

        if (proveedorEditar == null) {
            Notification.show("Error: No se ha seleccionado un proveedor para agregar el producto.");
            return;
        }

        if (proveedorEditar.getListaProductos().contains(nombreProducto)) {
            Notification.show("Error: El producto ya está asociado a este proveedor.");
            return;
        }

        proveedorEditar.getListaProductos().add(nombreProducto);

        grid.getDataProvider().refreshItem(proveedorEditar);

        productoTextField.clear();
        Notification.show("Producto agregado al proveedor correctamente.");
    }
}
