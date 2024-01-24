package com.farmaciafinal.views.ingresarcliente;

import com.farmaciafinal.models.Cliente;

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


import java.util.List;

import static com.farmaciafinal.utils.Utils.*;

@PageTitle("Ingresar Cliente")
@Route(value = "ingresar-cliente", layout = MainLayout.class)
@Uses(Icon.class)
public class IngresarClienteView extends Composite<VerticalLayout> {
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    FormLayout formLayout2Col = new FormLayout();
    TextField nombreCliente = new TextField();
    TextField telefonoCliente = new TextField();
    TextField direccionCliente = new TextField();
    TextField cedula = new TextField();

    HorizontalLayout layoutRow = new HorizontalLayout();
    Button guardar = new Button();
    Button cancelar = new Button();

   Cliente clienteEditar;

    String nombrecl, cedulaCliente, direccioncl;
    String telefonocl;

    Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    public IngresarClienteView() {

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Información del Cliente");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nombreCliente.setLabel("Nombre Cliente");
        telefonoCliente.setLabel("Telefono Cliente");
        cedula.setLabel("Cedula");
        direccionCliente.setLabel("Direccion");
        cedula.setRequired(true);
        cedula.setErrorMessage("Debe ingresar la cedula del cliente");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        guardar.setText("Save");
        guardar.setWidth("min-content");
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addClickListener(e -> {
            // Obtener los valores de los campos
            nombrecl = nombreCliente.getValue();


            // ¡Asegúrate de inicializar 'precioProducto' antes de usarlo!
            try {
                telefonocl = telefonoCliente.getValue();
            } catch (NumberFormatException ex) {
                Notification.show("Error: El precio debe ser un número válido");
                return; // Salir del método si el precio no es válido
            }

            direccioncl = direccionCliente.getValue();
            cedulaCliente = cedula.getValue();
            Cliente cliente = new Cliente(nombrecl, cedulaCliente,telefonocl, direccioncl);
            // Agregar el producto a la lista de productos
            listaCliente.add(cliente);

            // Actualizar el DataProvider del Grid
            grid.setItems(listaCliente);
        });

        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            // Borrar todos los datos de los TextField
            nombreCliente.clear();
            telefonoCliente.clear();
            cedula.clear();

            // Puedes agregar más líneas según la cantidad de TextField que tengas

            guardar.getUI().ifPresent(ui ->
                    ui.getPage().reload()); // Puedes usar reload() para recargar la página si es necesario
        });


        getContent().add(layoutColumn2);

        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombreCliente);
        formLayout2Col.add(telefonoCliente);
        formLayout2Col.add(cedula);
        formLayout2Col.add(direccionCliente);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar);
        layoutRow.add(cancelar);
        VerticalLayout layoutColumn2 = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");

        grid.addColumn(Cliente::getCedula).setHeader("Cedula").setAutoWidth(true);
        grid.addColumn(Cliente::getNombre).setHeader("Nombre Cliente").setAutoWidth(true);
        grid.addColumn(Cliente::getTelefono).setHeader("Telefono").setAutoWidth(true);
        grid.addColumn(Cliente::getDireccion).setHeader("Direccion").setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(cliente -> {
                    Button botonBorrar = new Button();
                    botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    botonBorrar.addClickListener(e -> {
                        listaCliente.remove(cliente);
                        grid.getDataProvider().refreshAll();
                    });
                    botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

                    Button botonEditar = new Button();
                    botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    botonEditar.addClickListener(e -> {
                        // Verificar si el código del producto ya existe (assumiendo que productoEditar es una instancia válida)
                        if (productoYaExiste(cedula.getValue())) {
                            nombreCliente.setReadOnly(false);
                            telefonoCliente.setReadOnly(false);
                            cedula.setReadOnly(false);
                            // Realizar la lógica de edición según el tipo de producto
                            clienteEditar.setNombre(nombrecl);
                            clienteEditar.setDireccion(direccioncl);

                            clienteEditar.setTelefono(telefonocl);
                            clienteEditar.setCedula(cedulaCliente);
                            clienteEditar.setDireccion(String.valueOf(direccionCliente));
                            guardar.addClickListener(guardarEvent -> {
                                // Lógica para guardar los datos (reemplaza esto con tu lógica real)
                                grid.setItems(clienteEditar);
                            });

                        }});
                    botonEditar.setIcon(new Icon(VaadinIcon.EDIT));




                    HorizontalLayout buttons = new HorizontalLayout(botonBorrar,botonEditar);
                    return buttons;
                })).setHeader("Manage").setAutoWidth(true);


        List<Cliente> clientes = listaCliente;
        grid.setItems(clientes);
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
