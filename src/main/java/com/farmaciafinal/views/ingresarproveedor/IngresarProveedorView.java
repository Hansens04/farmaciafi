package com.farmaciafinal.views.ingresarproveedor;

import com.farmaciafinal.models.Producto;
import com.farmaciafinal.models.Proveedor;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.farmaciafinal.utils.Utils.listaProveedores;

@PageTitle("Ingresar Proveedor")
@Route(value = "ingresar-proveedor", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class IngresarProveedorView extends Composite<VerticalLayout> {
    // Declaración de componentes de la interfaz de usuario
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    FormLayout formLayout2Col = new FormLayout();
    TextField nombreProveedor = new TextField();
    TextField telefono = new TextField();
    TextField direccion = new TextField();
    TextField codigo = new TextField();
    HorizontalLayout layoutRow = new HorizontalLayout();
    Button guardar = new Button();
    Button cancelar = new Button();
    Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);
    Proveedor proveedorEditar;
    boolean modoEdicion = false;
    private ComboBox<Producto> productoComboBox = new ComboBox<>("Seleccione un producto");

    // Constructor de la vista
    public IngresarProveedorView() {
        // Configuración de diseño y estilo de la vista
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Información del Proveedor");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nombreProveedor.setLabel("Nombre Proveedor");
        telefono.setLabel("Telefono Proveedor");
        codigo.setLabel("Código");
        direccion.setLabel("Direccion");
        layoutRow.addClassName("gap-m");
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        guardar.setText("Save");
        guardar.setWidth("min-content");
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Agregar los productos al ComboBox
        productoComboBox.setItems(Utils.listaProdcuto);
        productoComboBox.setItemLabelGenerator(Producto::getNombreProducto);

        // Lógica para guardar un nuevo proveedor o actualizar uno existente
        guardar.addClickListener(e -> {
            if (modoEdicion) {
                guardarEdicion();
            } else {
                guardarNuevoProveedor();
            }
        });

        // Configuración del botón de cancelar
        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            limpiarCampos();
            if (modoEdicion) {
                modoEdicion = false;
                cancelarEdicion();
            }
        });

        // Agregar componentes al diseño
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombreProveedor, telefono, codigo, direccion, productoComboBox);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar);
        layoutRow.add(cancelar);
        layoutColumn2.add(grid);

        // Configuración de columnas en el grid
        grid.addColumn(Proveedor::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Proveedor::getNombreProveedor).setHeader("Nombre Proveedor").setAutoWidth(true);
        grid.addColumn(Proveedor::getTelefonoProveedor).setHeader("Telefono").setAutoWidth(true);
        grid.addColumn(Proveedor::getDireccion).setHeader("Direccion").setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(proveedor -> {
            // Botón para borrar un proveedor
            Button botonBorrar = new Button();
            botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
            botonBorrar.addClickListener(event -> {
                listaProveedores.remove(proveedor);
                grid.getDataProvider().refreshAll();
            });
            botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

            // Botón para editar un proveedor
            Button botonEditar = new Button();
            botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            botonEditar.addClickListener(event -> editarProveedor(proveedor));
            botonEditar.setIcon(new Icon(VaadinIcon.EDIT));

            // Diseño horizontal para los botones de acción
            HorizontalLayout buttons = new HorizontalLayout(botonBorrar, botonEditar);
            return buttons;
        })).setHeader("Manage").setAutoWidth(true);

        // Configuración de la lista de proveedores en el grid
        grid.setItems(listaProveedores);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    // Método para guardar un nuevo proveedor
    private void guardarNuevoProveedor() {
        String nombreProve = nombreProveedor.getValue();
        String telefonoP = telefono.getValue();
        String direccionProveedor = direccion.getValue();
        String codigoProveedor = codigo.getValue();

        // Verificar que todos los campos estén completos
        if (nombreProve.isEmpty() || telefonoP.isEmpty() || direccionProveedor.isEmpty() || codigoProveedor.isEmpty()) {
            Notification.show("Error: Todos los campos son obligatorios.");
            return;
        }

        // Validar si el código del proveedor ya existe
        if (codigoProveedorExistente(codigoProveedor)) {
            Notification.show("Error: El código del proveedor ya existe.");
            return;
        }

        // Validar formato del teléfono (solo números)
        if (!esNumero(telefonoP)) {
            Notification.show("Error: El teléfono debe contener solo números.");
            return;
        }

        // Validar formato del código (solo números)
        if (!esNumero(codigoProveedor)) {
            Notification.show("Error: El código debe contener solo números.");
            return;
        }

        // Validar que el nombre contenga solo letras
        if (!contieneSoloLetras(nombreProve)) {
            Notification.show("Error: El nombre del proveedor debe contener solo letras.");
            return;
        }

        Proveedor proveedor = new Proveedor(nombreProve, telefonoP, codigoProveedor, direccionProveedor, new ArrayList<>());
        listaProveedores.add(proveedor);

        grid.setItems(listaProveedores);

        limpiarCampos();
    }

    // Método para verificar si el código del proveedor ya existe
    private boolean codigoProveedorExistente(String codigoProveedor) {
        // Verificar si el código del proveedor ya existe en la lista de proveedores
        return listaProveedores.stream().anyMatch(proveedor -> proveedor.getCodigo().equals(codigoProveedor));
    }

    // Método para verificar si una cadena contiene solo números
    private boolean esNumero(String cadena) {
        // Verifica si la cadena solo contiene números
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    // Método para verificar si una cadena contiene solo letras
    private boolean contieneSoloLetras(String cadena) {
        // Verifica si la cadena solo contiene letras
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    // Método para editar un proveedor
    private void editarProveedor(Proveedor proveedor) {
        proveedorEditar = proveedor;
        modoEdicion = true;

        // Asignar valores del proveedor a los campos de texto
        nombreProveedor.setValue(proveedor.getNombreProveedor());
        telefono.setValue(proveedor.getTelefonoProveedor());
        direccion.setValue(proveedor.getDireccion());
        codigo.setValue(proveedor.getCodigo());

        // Cambiar texto del botón guardar y remover el botón de cancelar
        guardar.setText("Update");
        guardar.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        layoutRow.remove(cancelar);
    }

    // Método para guardar la edición de un proveedor
    private void guardarEdicion() {
        String nombreProve = nombreProveedor.getValue();
        String telefonoP = telefono.getValue();
        String direccionProveedor = direccion.getValue();

        // Verificar que todos los campos estén completos
        if (nombreProve.isEmpty() || telefonoP.isEmpty() || direccionProveedor.isEmpty()) {
            Notification.show("Error: Todos los campos son obligatorios.");
            return;
        }

        // Actualizar los datos del proveedor
        proveedorEditar.setNombreProveedor(nombreProve);
        proveedorEditar.setTelefonoProveedor(telefonoP);
        proveedorEditar.setDireccion(direccionProveedor);

        // Remover el proveedor antiguo y agregar el proveedor actualizado
        listaProveedores.remove(proveedorEditar);
        listaProveedores.add(proveedorEditar);

        // Actualizar el Grid con la lista actualizada de proveedores
        grid.setItems(listaProveedores);

        // Limpiar los campos y volver al modo de edición
        limpiarCampos();
        modoEdicion = false;
        guardar.setText("Save");
        guardar.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow.add(cancelar);
    }

    // Método para cancelar la edición de un proveedor
    private void cancelarEdicion() {
        limpiarCampos();
        modoEdicion = false;
        guardar.setText("Save");
        guardar.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutRow.add(cancelar);
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        nombreProveedor.clear();
        telefono.clear();
        direccion.clear();
        codigo.clear();
    }
}
