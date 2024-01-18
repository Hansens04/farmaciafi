package com.farmaciafinal.views.pedidosproveedor;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Pedidos Proveedor")
@Route(value = "pedidos-proveedor", layout = MainLayout.class)
@Uses(Icon.class)
public class PedidosProveedorView extends Composite<VerticalLayout> {

    public PedidosProveedorView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
