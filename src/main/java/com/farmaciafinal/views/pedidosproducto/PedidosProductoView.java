package com.farmaciafinal.views.pedidosproducto;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Pedidos Producto")
@Route(value = "pedidos-producto", layout = MainLayout.class)
@Uses(Icon.class)
public class PedidosProductoView extends Composite<VerticalLayout> {

    public PedidosProductoView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
