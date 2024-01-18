package com.farmaciafinal.views.ingresarcliente;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Ingresar Cliente")
@Route(value = "ingresar-cliente", layout = MainLayout.class)
@Uses(Icon.class)
public class IngresarClienteView extends Composite<VerticalLayout> {

    public IngresarClienteView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
