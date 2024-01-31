package com.farmaciafinal.views.ingresarproductos;

import com.farmaciafinal.models.Producto;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Comparator;
import java.util.List;
import static com.farmaciafinal.utils.Utils.listaProdcuto;

@PageTitle("Ingresar Productos")
@Route(value = "ingresar-productos", layout = MainLayout.class)
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
    Grid<Producto> grid = new Grid<>();

    public IngresarProductosView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
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
        codigo.setReadOnly(false); // Hacer el campo de código de solo lectura
        codigo.setErrorMessage("Debe ingresar el código del producto");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        guardar.setText("Save");
        guardar.setWidth("min-content");
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        guardar.addClickListener(e -> {
            String nombreProducto = nombre.getValue();
            int cantidadProducto = stock.getValue().intValue();
            int cantidadminimaProducto = cantidadminima.getValue().intValue();
            double precioProducto;
            try {
                precioProducto = Double.parseDouble(precio.getValue());
            } catch (NumberFormatException ex) {
                Notification.show("Error: El precio debe ser un número válido");
                return;
            }
            String descripcionP = descripcion.getValue();
            String codigoProducto = codigo.getValue();

            if (productoYaExiste(codigoProducto)) {
                if (productoEditar != null) {
                    productoEditar.setNombreProducto(nombreProducto);
                    productoEditar.setStock(cantidadProducto);
                    productoEditar.setCantidadMinima(cantidadminimaProducto);
                    productoEditar.setPrecio(precioProducto);
                    productoEditar.setDescripcion(descripcionP);
                } else {
                    Notification.show("Error: El código del producto ya existe");
                    return;
                }
            } else {
                Producto producto = new Producto(codigoProducto, nombreProducto, descripcionP, cantidadProducto, cantidadminimaProducto, precioProducto);
                listaProdcuto.add(producto);
            }

            limpiarCampos();
            grid.setItems(listaProdcuto);
        });

        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> limpiarCampos());

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombre, precio, codigo, stock, cantidadminima, descripcion);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar, cancelar);
        layoutColumn2.add(grid);

        grid.addColumn(Producto::getIdProducto).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Producto::getNombreProducto).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Producto::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Producto::getDescripcion).setHeader("Descripcion").setAutoWidth(true);
        grid.addColumn(Producto::getStock).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(Producto::getCantidadMinima).setHeader("CantidadMinima").setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(producto -> {
            Button botonBorrar = new Button();
            botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
            botonBorrar.addClickListener(event -> {
                listaProdcuto.remove(producto);
                grid.getDataProvider().refreshAll();
            });
            botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

            Button botonEditar = new Button();
            botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            botonEditar.addClickListener(event -> {
                productoEditar = producto;
                nombre.setValue(producto.getNombreProducto());
                stock.setValue((double) producto.getStock());
                cantidadminima.setValue((double) producto.getCantidadMinima());
                precio.setValue(String.valueOf(producto.getPrecio()));
                descripcion.setValue(producto.getDescripcion());
                // No permitir la edición del código al editar
                codigo.setValue(producto.getIdProducto());
                codigo.setReadOnly(true);
                guardar.setText("Update");
            });
            botonEditar.setIcon(new Icon(VaadinIcon.EDIT));

            HorizontalLayout buttons = new HorizontalLayout(botonBorrar, botonEditar);
            return buttons;
        })).setHeader("Manage").setAutoWidth(true);

        grid.setItems(listaProdcuto);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    private boolean productoYaExiste(String codigoProducto) {
        return listaProdcuto.stream().anyMatch(producto -> producto.getIdProducto().equals(codigoProducto));
    }

    private void limpiarCampos() {
        nombre.clear();
        stock.clear();
        cantidadminima.clear();
        precio.clear();
        descripcion.clear();
        codigo.clear();
        guardar.setText("Save");
        codigo.setReadOnly(false);
        productoEditar = null;
    }
}
