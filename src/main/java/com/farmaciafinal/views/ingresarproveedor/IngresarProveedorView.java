package com.farmaciafinal.views.ingresarproveedor;

import com.farmaciafinal.models.Proveedor;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;


import java.util.ArrayList;
import java.util.List;

import static com.farmaciafinal.utils.Utils.listaProdcuto;
import static com.farmaciafinal.utils.Utils.listaProveedores;

@PageTitle("Ingresar Proveedor")
@Route(value = "ingresar-proveedor", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class IngresarProveedorView extends Composite<VerticalLayout> {
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

    Proveedor proveedorEditar;

    String nombreProve, codigoProveedor, direccionProveedor;
    String telefonoP;
    List<String> listaproducto =new ArrayList<>();

    Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);
    public IngresarProveedorView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
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
        codigo.setRequired(true);
        codigo.setErrorMessage("Debe ingresar el código del proveedor");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        guardar.setText("Save");
        guardar.setWidth("min-content");
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addClickListener(e -> {
            // Obtener los valores de los campos
            nombreProve = nombreProveedor.getValue();


            // ¡Asegúrate de inicializar 'precioProducto' antes de usarlo!
            try {
                telefonoP = telefono.getValue();
            } catch (NumberFormatException ex) {
                Notification.show("Error: El precio debe ser un número válido");
                return; // Salir del método si el precio no es válido
            }

            direccionProveedor = direccion.getValue();
            codigoProveedor = codigo.getValue();
            Proveedor proveedor = new Proveedor(nombreProve,telefonoP,codigoProveedor,direccionProveedor,listaproducto);
            // Agregar el producto a la lista de productos
            listaProveedores.add(proveedor);

            // Actualizar el DataProvider del Grid
            grid.setItems(listaProveedores);
        });

        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            // Borrar todos los datos de los TextField
            nombreProveedor.clear();
            telefono.clear();
            codigo.clear();

            // Puedes agregar más líneas según la cantidad de TextField que tengas

            guardar.getUI().ifPresent(ui ->
                    ui.getPage().reload()); // Puedes usar reload() para recargar la página si es necesario
        });


        getContent().add(layoutColumn2);

        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombreProveedor);
        formLayout2Col.add(telefono);
        formLayout2Col.add(codigo);
        formLayout2Col.add(direccion);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar);
        layoutRow.add(cancelar);
        VerticalLayout layoutColumn2 = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");

        grid.addColumn(Proveedor::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Proveedor::getNombreProveedor).setHeader("Nombre Proveedor").setAutoWidth(true);
        grid.addColumn(Proveedor::getTelefonoProveedor).setHeader("Telefono").setAutoWidth(true);
        grid.addColumn(Proveedor::getDireccion).setHeader("Direccion").setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(proveedor -> {
                    Button botonBorrar = new Button();
                    botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    botonBorrar.addClickListener(e -> {
                        listaProveedores.remove(proveedor);
                        grid.getDataProvider().refreshAll();
                    });
                    botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

                    Button botonEditar = new Button();
                    botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    botonEditar.addClickListener(e -> {
                        // Verificar si el código del producto ya existe (assumiendo que productoEditar es una instancia válida)
                        if (productoYaExiste(codigo.getValue())) {
                            nombreProveedor.setReadOnly(false);
                            telefono.setReadOnly(false);
                            codigo.setReadOnly(true);
                            // Realizar la lógica de edición según el tipo de producto
                            proveedorEditar.setNombreProveedor(nombreProve);
                            proveedorEditar.setDireccion(direccionProveedor);

                            proveedorEditar.setTelefonoProveedor(telefonoP);
                            proveedorEditar.setDireccion(String.valueOf(direccion));
                            guardar.addClickListener(guardarEvent -> {
                                // Lógica para guardar los datos (reemplaza esto con tu lógica real)
                                grid.setItems(proveedorEditar);
                            });

                        }});
                    botonEditar.setIcon(new Icon(VaadinIcon.EDIT));




                    HorizontalLayout buttons = new HorizontalLayout(botonBorrar,botonEditar);
                    return buttons;
                })).setHeader("Manage").setAutoWidth(true);


        List<Proveedor> proveedors = listaProveedores;
        grid.setItems(proveedors);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        layoutColumn2.add(guardar,grid);


        getContent().add(layoutColumn2);
    }
    // Método para verificar si el código del producto ya existe
    private boolean productoYaExiste(String codigoProducto) {
        // Supongamos que tienes una lista llamada "listaProductos" que contiene objetos Producto

        // Verificar si algún producto en la lista tiene el mismo código
        return listaProdcuto.stream()
                .anyMatch(producto -> producto.getIdProducto().equals(codigoProducto));
    }



}