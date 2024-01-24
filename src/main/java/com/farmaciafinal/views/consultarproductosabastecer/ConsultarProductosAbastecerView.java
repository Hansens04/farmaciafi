package com.farmaciafinal.views.consultarproductosabastecer;

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

@PageTitle("Consultar Productos Abastecer")
@Route(value = "consultar-Productos-Abastecer", layout = MainLayout.class)
public class ConsultarProductosAbastecerView extends Composite<VerticalLayout> {
    private Grid<Producto> grid = new Grid<>(Producto.class);

    public ConsultarProductosAbastecerView() {
        try {
            VerticalLayout layoutColumn2 = new VerticalLayout();
            getContent().setWidth("100%");
            getContent().getStyle().set("flex-grow", "1");
            layoutColumn2.setWidth("100%");
            layoutColumn2.getStyle().set("flex-grow", "1");

            List<Producto> productosAbastecer = listaProdcuto.stream()
                    .filter(producto -> producto.getCantidadMinima() > producto.getStock())
                    .collect(Collectors.toList());
            grid.setItems(productosAbastecer);
            getContent().add(grid);
        } catch (Exception e) {
            // Manejar la excepción de manera adecuada, por ejemplo, mostrando un mensaje de error.
            e.printStackTrace();
        }
    }
}
