package com.farmaciafinal.views.facturascliente;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Facturas Cliente")
@Route(value = "facturas-cliente", layout = MainLayout.class)
@Uses(Icon.class)
public class FacturasClienteView extends Composite<VerticalLayout> {

    public FacturasClienteView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
