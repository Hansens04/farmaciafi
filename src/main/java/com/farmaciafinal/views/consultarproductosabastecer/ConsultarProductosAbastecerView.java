package com.farmaciafinal.views.consultarproductosabastecer;

// Importaciones necesarias
import com.farmaciafinal.models.Producto;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

import static com.farmaciafinal.utils.Utils.listaProdcuto;

// Define el título de la página y la ruta para esta vista
@PageTitle("Consultar Productos Abastecer")
@Route(value = "consultar-Productos-Abastecer", layout = MainLayout.class)
public class ConsultarProductosAbastecerView extends Composite<VerticalLayout> {
    // Declaración del grid donde se mostrará la información
    private Grid<Producto> grid = new Grid<>(Producto.class);

    // Constructor de la vista
    public ConsultarProductosAbastecerView() {
        // Configuración del diseño de la vista
        try {
            VerticalLayout layoutColumn2 = new VerticalLayout();
            getContent().setWidth("100%");
            getContent().getStyle().set("flex-grow", "1");
            layoutColumn2.setWidth("100%");
            layoutColumn2.getStyle().set("flex-grow", "1");

            // Filtrar los productos que necesitan ser abastecidos
            List<Producto> productosAbastecer = listaProdcuto.stream()
                    .filter(producto -> producto.getCantidadMinima() > producto.getStock())
                    .collect(Collectors.toList());

            // Establecer los datos en el grid
            grid.setItems(productosAbastecer);

            // Añadir el grid al contenido de la vista
            getContent().add(grid);
        } catch (Exception e) {
            // Manejar la excepción de manera adecuada, por ejemplo, mostrando un mensaje de error.
            e.printStackTrace();
        }
    }
}
