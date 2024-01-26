package com.farmaciafinal.views.pedidosproveedor;

import com.farmaciafinal.models.EncabezadoPedido;
import com.farmaciafinal.models.Proveedor;
import com.farmaciafinal.utils.Utils;
import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Pedidos Proveedor")
@Route(value = "pedidos-proveedor", layout = MainLayout.class)
@Uses(Icon.class)
public class PedidosProveedorView extends Composite<VerticalLayout> {
    // Componentes de la interfaz de usuario
    private ComboBox<Proveedor> proveedorComboBox;
    private Grid<EncabezadoPedido> pedidoGrid;

    // Constructor de la vista
    public PedidosProveedorView() {
        // Configuración del diseño y estilo de la vista
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");

        // Inicializar ComboBox para seleccionar el proveedor
        proveedorComboBox = new ComboBox<>("Proveedor");
        proveedorComboBox.setItems(Utils.listaProveedores);
        proveedorComboBox.setItemLabelGenerator(Proveedor::getNombreProveedor);
        proveedorComboBox.setWidth("300px");
        proveedorComboBox.addValueChangeListener(event -> buscarPedidosPorProveedor(event.getValue()));

        // Inicializar Grid para mostrar los pedidos asociados al proveedor seleccionado
        pedidoGrid = new Grid<>(EncabezadoPedido.class, false);
        pedidoGrid.setColumns("codigoPedido", "fechaEnvio", "producto.nombreProducto", "cantidad", "total", "estado");
        pedidoGrid.setWidth("100%");

        // Agregar componentes al diseño
        getContent().add(proveedorComboBox, pedidoGrid);
    }

    // Método para buscar pedidos por proveedor
    private void buscarPedidosPorProveedor(Proveedor proveedorSeleccionado) {
        if (proveedorSeleccionado == null) {
            // Si no se ha seleccionado ningún proveedor, limpiar el grid
            pedidoGrid.setItems(List.of());
            return;
        }

        // Filtrar los pedidos por proveedor seleccionado
        List<EncabezadoPedido> pedidosPorProveedor = Utils.listaEncabezadoPedido.stream()
                .filter(pedido -> pedido.getProveedor().equals(proveedorSeleccionado))
                .collect(Collectors.toList());

        // Mostrar los pedidos encontrados en el grid
        pedidoGrid.setItems(pedidosPorProveedor);
    }
}
