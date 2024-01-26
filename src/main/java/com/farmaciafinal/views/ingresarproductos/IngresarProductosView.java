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
    // Declaración de componentes de la interfaz de usuario
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
    Grid<Producto>grid=new Grid<>();

    // Constructor de la vista
    public IngresarProductosView() {
        // Configuración de diseño y estilo de la vista
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

        // Lógica para guardar un producto
        guardar.addClickListener(e -> {
            // Obtener los valores de los campos
            String nombreProducto = nombre.getValue();
            int cantidadProducto = stock.getValue().intValue();
            int cantidadminimaProducto = cantidadminima.getValue().intValue();

            // ¡Asegúrate de inicializar 'precioProducto' antes de usarlo!
            double precioProducto;
            try {
                precioProducto = Double.parseDouble(precio.getValue());
            } catch (NumberFormatException ex) {
                Notification.show("Error: El precio debe ser un número válido");
                return; // Salir del método si el precio no es válido
            }

            String descripcionP = descripcion.getValue();
            String codigoProducto = codigo.getValue();

            // Verificar si el código del producto ya existe
            if (productoYaExiste(codigoProducto)) {
                Notification.show("Error: El código del producto ya existe");
                return; // Salir del método si el código ya existe
            }

            // Crear un nuevo objeto Producto
            Producto producto = new Producto(codigoProducto, nombreProducto, descripcionP, cantidadProducto, cantidadminimaProducto, precioProducto);

            // Agregar el producto a la lista de productos
            listaProdcuto.add(producto);

            // Actualizar el grid con la lista de productos
            grid.setItems(listaProdcuto);
        });

        cancelar.setText("Cancel");
        cancelar.setWidth("min-content");
        cancelar.addClickListener(e -> limpiarCampos());

        // Agregar componentes al diseño
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombre, precio, codigo, stock, cantidadminima, descripcion);
        layoutColumn2.add(layoutRow);
        layoutRow.add(guardar, cancelar);
        layoutColumn2.add(grid);

        // Configuración de columnas en el grid
        grid.addColumn(Producto::getIdProducto).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Producto::getNombreProducto).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Producto::getPrecio).setHeader("Precio").setAutoWidth(true);
        grid.addColumn(Producto::getDescripcion).setHeader("Descripcion").setAutoWidth(true);
        grid.addColumn(Producto::getStock).setHeader("Cantidad").setAutoWidth(true);
        grid.addColumn(Producto::getCantidadMinima).setHeader("CantidadMinima").setAutoWidth(true);
        grid.addColumn(new ComponentRenderer<>(producto -> {
            // Botón para borrar un producto
            Button botonBorrar = new Button();
            botonBorrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
            botonBorrar.addClickListener(event -> {
                listaProdcuto.remove(producto);
                grid.getDataProvider().refreshAll();
            });
            botonBorrar.setIcon(new Icon(VaadinIcon.TRASH));

            // Botón para editar un producto
            Button botonEditar = new Button();
            botonEditar.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            botonEditar.addClickListener(event -> {
                // Asignar el producto a editar y mostrar los detalles en los campos de texto
                productoEditar = producto;
                nombre.setValue(producto.getNombreProducto());
                stock.setValue((double) producto.getStock());
                cantidadminima.setValue((double) producto.getCantidadMinima());
                precio.setValue(String.valueOf(producto.getPrecio()));
                descripcion.setValue(producto.getDescripcion());
                codigo.setReadOnly(true);
                guardar.setText("Update");
            });
            botonEditar.setIcon(new Icon(VaadinIcon.EDIT));

            // Diseño horizontal para los botones de acción
            HorizontalLayout buttons = new HorizontalLayout(botonBorrar, botonEditar);
            return buttons;
        })).setHeader("Manage").setAutoWidth(true);

        // Mostrar la lista de productos en el grid
        grid.setItems(listaProdcuto);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    // Método para verificar si el código del producto ya existe
    private boolean productoYaExiste(String codigoProducto) {
        return listaProdcuto.stream().anyMatch(producto -> producto.getIdProducto().equals(codigoProducto));
    }

    // Método para limpiar los campos de texto después de guardar o cancelar
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
