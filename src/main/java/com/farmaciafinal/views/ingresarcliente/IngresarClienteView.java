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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.farmaciafinal.utils.Utils.*;

// Define el título de la página y la ruta para esta vista
@PageTitle("Ingresar Cliente")
@Route(value = "ingresar-cliente", layout = MainLayout.class)
@Uses(Icon.class)
public class IngresarClienteView extends Composite<VerticalLayout> {
    // Declaración de componentes de la interfaz de usuario
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
    Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    Cliente clienteEditar;
    private Binder<Cliente> binder = new Binder<>(Cliente.class);

    // Constructor de la vista
    public IngresarClienteView() {
        // Configuración de diseño y estilo de la vista
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

        // Configurar la validación del campo de nombre del cliente
        binder.forField(nombreCliente)
                .asRequired("El nombre del cliente es obligatorio")
                .withValidator(nombre -> contieneSoloLetras(nombre), "El nombre del cliente debe contener solo letras")
                .bind(Cliente::getNombre, Cliente::setNombre);

        // Configurar la validación del campo de teléfono del cliente
        binder.forField(telefonoCliente)
                .withValidator(telefono -> contieneSoloNumeros(telefono), "El teléfono debe contener solo números")
                .bind(Cliente::getTelefono, Cliente::setTelefono);

        // Configurar la validación del campo de cédula del cliente
        binder.forField(cedula)
                .withValidator(cedula -> contieneSoloNumeros(cedula), "La cédula debe contener solo números")
                .bind(Cliente::getCedula, Cliente::setCedula);

        // Lógica para guardar un cliente
        guardar.addClickListener(e -> {
            // Validar si los campos son válidos
            if (binder.validate().isOk()) {
                // Obtener los valores de los campos
                String nombreCliente = this.nombreCliente.getValue();
                String telefonoCliente = this.telefonoCliente.getValue();
                String cedulaCliente = this.cedula.getValue();
                String direccionCliente = this.direccionCliente.getValue();

                // Crear un nuevo objeto Cliente
                Cliente cliente = new Cliente(nombreCliente, cedulaCliente, telefonoCliente, direccionCliente);

                // Agregar el cliente a la lista de clientes
                listaCliente.add(cliente);

                // Actualizar el grid con la lista de clientes
                grid.setItems(listaCliente);

                // Limpiar campos después de guardar
                limpiarCampos();
            }
        });

        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> limpiarCampos());

        // Agregar componentes al diseño
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombreCliente, telefonoCliente, cedula, direccionCliente);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar, cancelar);
        layoutColumn2.add(grid);

        // Configuración de columnas en el grid
        grid.addColumn(Cliente::getCedula).setHeader("Cedula").setAutoWidth(true);
        grid.addColumn(Cliente::getNombre).setHeader("Nombre Cliente").setAutoWidth(true);
        grid.addColumn(Cliente::getTelefono).setHeader("Telefono").setAutoWidth(true);
        grid.addColumn(Cliente::getDireccion).setHeader("Direccion").setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(cliente -> {
            // Botón para borrar un cliente
            Button botonBorrar = new Button();
            botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
            botonBorrar.addClickListener(event -> {
                listaCliente.remove(cliente);
                grid.getDataProvider().refreshAll();
            });
            botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

            // Botón para editar un cliente
            Button botonEditar = new Button();
            botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            botonEditar.addClickListener(event -> {
                // Asignar el cliente a editar y mostrar los detalles en los campos de texto
                clienteEditar = cliente;
                this.nombreCliente.setValue(cliente.getNombre());
                this.telefonoCliente.setValue(cliente.getTelefono());
                this.cedula.setValue(cliente.getCedula());
                this.direccionCliente.setValue(cliente.getDireccion());
                guardar.setText("Update");
            });
            botonEditar.setIcon(new Icon(VaadinIcon.EDIT));

            // Diseño horizontal para los botones de acción
            HorizontalLayout buttons = new HorizontalLayout(botonBorrar, botonEditar);
            return buttons;
        })).setHeader("Manage").setAutoWidth(true);

        // Mostrar la lista de clientes en el grid
        grid.setItems(listaCliente);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    // Método para limpiar los campos de texto después de guardar o cancelar
    private void limpiarCampos() {
        nombreCliente.clear();
        telefonoCliente.clear();
        cedula.clear();
        direccionCliente.clear();
        guardar.setText("Save");
        clienteEditar = null;
    }

    // Método para verificar si una cadena contiene solo letras
    private boolean contieneSoloLetras(String cadena) {
        // Verifica si la cadena solo contiene letras
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }

    // Método para verificar si una cadena contiene solo números
    private boolean contieneSoloNumeros(String cadena) {
        // Verifica si la cadena solo contiene números
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
    }
}
