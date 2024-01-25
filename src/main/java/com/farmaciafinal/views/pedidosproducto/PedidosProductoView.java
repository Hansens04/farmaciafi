package com.farmaciafinal.views.pedidosproducto;

import com.farmaciafinal.models.EncabezadoPedido;
import com.farmaciafinal.models.Producto;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Pedidos Producto")
@Route(value = "pedidos-producto", layout = MainLayout.class)
public class PedidosProductoView extends Composite<VerticalLayout> {
    private Select<Producto> productoSelect;
    private Grid<EncabezadoPedido> grid;

    public PedidosProductoView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        // Inicializar ComboBox para seleccionar el producto
        productoSelect = new Select<>();
        productoSelect.setLabel("Seleccione un producto");
        productoSelect.setItems(Utils.listaProdcuto);
        productoSelect.setItemLabelGenerator(Producto::getNombreProducto);
        productoSelect.setWidth("300px");
        productoSelect.addValueChangeListener(event -> buscarPedidosPorProducto(event.getValue()));

        // Inicializar Grid para mostrar los pedidos asociados al producto seleccionado
        grid = new Grid<>(EncabezadoPedido.class, false);
        grid.setColumns("codigoPedido", "fechaEnvio", "proveedor.nombreProveedor", "cantidad", "total", "estado");
        grid.setWidth("100%");

        // Agregar componentes al diseño
        getContent().add(productoSelect, grid);
    }

    private void buscarPedidosPorProducto(Producto productoSeleccionado) {
        if (productoSeleccionado == null) {
            // Si no se ha seleccionado ningún producto, limpiar el grid
            grid.setItems(List.of());
            return;
        }

        // Filtrar los pedidos por producto seleccionado
        List<EncabezadoPedido> pedidosPorProducto = Utils.listaEncabezadoPedido.stream()
                .filter(pedido -> pedido.getProducto().equals(productoSeleccionado))
                .collect(Collectors.toList());

        // Mostrar los pedidos encontrados en el grid
        grid.setItems(pedidosPorProducto);
    }
}
