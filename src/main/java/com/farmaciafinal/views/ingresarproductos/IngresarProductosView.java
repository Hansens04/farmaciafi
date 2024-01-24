package com.farmaciafinal.views.ingresarproductos;

import com.farmaciafinal.models.*;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

import static com.farmaciafinal.utils.Utils.listaProdcuto;

@PageTitle("Ingresar Productos")
@Route(value = "ingresar-Productos", layout = MainLayout.class)
public class IngresarProductosView extends Composite<VerticalLayout> {
    VerticalLayout layoutColumn2 = new VerticalLayout();
    H3 h3 = new H3();
    FormLayout formLayout2Col = new FormLayout();
    TextField nombre = new TextField();
    NumberField stock = new NumberField();
    NumberField cantidadminima = new NumberField();
    TextField precio = new TextField();
    TextField descripcion = new TextField();
    TextField codigo = new TextField();

    HorizontalLayout layoutRow = new HorizontalLayout();
    Button guardar = new Button();
    Button cancelar = new Button();
    Producto productoEditar;

    int cantidadProducto, cantidadminimaProducto;
    String nombreProducto, codigoProducto, descripcionP;

    double precioProducto;
    Grid<Producto> grid = new Grid<>(Producto.class, false);
    public IngresarProductosView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Información del Producto");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nombre.setLabel("Nombre");
        stock.setLabel("Stock");
        stock.setWidth("min-content");
        cantidadminima.setLabel("Cantidad minima");
        cantidadminima.setWidth("min-content");
        precio.setLabel("Precio");
        codigo.setLabel("Código");
        descripcion.setLabel("Descripcion");
        codigo.setRequired(true);
        codigo.setErrorMessage("Debe ingresar el código del producto");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        guardar.setText("Save");
        guardar.setWidth("min-content");
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addClickListener(e -> {
            // Obtener los valores de los campos
            nombreProducto = nombre.getValue();
            cantidadProducto = stock.getValue().intValue();
            cantidadminimaProducto = cantidadminima.getValue().intValue();

            // ¡Asegúrate de inicializar 'precioProducto' antes de usarlo!
            try {
                precioProducto = Double.parseDouble(precio.getValue());
            } catch (NumberFormatException ex) {
                Notification.show("Error: El precio debe ser un número válido");
                return; // Salir del método si el precio no es válido
            }

            descripcionP = descripcion.getValue();
            codigoProducto = codigo.getValue();

            // Verificar si el código del producto ya existe
            if (productoYaExiste(codigoProducto)) {
                Notification.show("Error: El código del producto ya existe");
                return; // Salir del método si el código ya existe
            }

            // Resto del código para crear el producto y agregarlo a la lista
            Producto producto = new Producto(codigoProducto, nombreProducto, descripcionP, cantidadProducto, cantidadminimaProducto, precioProducto);
            listaProdcuto.add(producto);
            grid.setItems(listaProdcuto);
        });

        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> {
            // Borrar todos los datos de los TextField
            nombre.clear();
            stock.clear();
            cantidadminima.clear();
            precio.clear();
            codigo.clear();

            // Puedes agregar más líneas según la cantidad de TextField que tengas

            guardar.getUI().ifPresent(ui ->
                    ui.getPage().reload()); // Puedes usar reload() para recargar la página si es necesario
        });


        getContent().add(layoutColumn2);

        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombre);
        formLayout2Col.add(precio);
        formLayout2Col.add(codigo);
        formLayout2Col.add(stock);
        formLayout2Col.add(cantidadminima);
        formLayout2Col.add(descripcion);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar);
        layoutRow.add(cancelar);
        VerticalLayout layoutColumn2 = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");

        grid.addColumn(Producto::getIdProducto).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Producto::getNombreProducto).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Producto::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Producto::getDescripcion).setHeader("Descripcion").setAutoWidth(true);
        grid.addColumn(Producto::getStock).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(Producto::getCantidadMinima).setHeader("CantidadMinima").setAutoWidth(true);
        grid.addColumn(
                new ComponentRenderer<>(producto -> {
                    Button botonBorrar = new Button();
                    botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    botonBorrar.addClickListener(e -> {
                        listaProdcuto.remove(producto);
                        grid.getDataProvider().refreshAll();
                    });
                    botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

                    Button botonEditar = new Button();
                    botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    botonEditar.addClickListener(e -> {
                            // Verificar si el código del producto ya existe (assumiendo que productoEditar es una instancia válida)
                            if (productoYaExiste(codigo.getValue())) {
                                nombre.setReadOnly(false);
                                stock.setReadOnly(false);
                                cantidadminima.setReadOnly(false);
                                precio.setReadOnly(false);
                                codigo.setReadOnly(true);
                                // Realizar la lógica de edición según el tipo de producto
                                productoEditar.setNombreProducto(nombreProducto);
                                productoEditar.setPrecio(precioProducto);
                                productoEditar.setStock(Integer.parseInt(String.valueOf(stock)));
                                productoEditar.setCantidadMinima(cantidadProducto);
                                productoEditar.setDescripcion(String.valueOf(descripcion));
                                guardar.addClickListener(guardarEvent -> {
                                    // Lógica para guardar los datos (reemplaza esto con tu lógica real)
                                    grid.setItems(productoEditar);
                                });

                    }});
                    botonEditar.setIcon(new Icon(VaadinIcon.EDIT));



                    HorizontalLayout buttons = new HorizontalLayout(botonBorrar,botonEditar);
                    return buttons;
                })).setHeader("Manage").setAutoWidth(true);


        List<Producto> productos = listaProdcuto;
        grid.setItems(productos);
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
