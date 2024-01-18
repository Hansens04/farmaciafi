package com.farmaciafinal.views.consultarproductosabastecer;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Consultar Productos Abastecer")
@Route(value = "consultar-Productos-Abastecer", layout = MainLayout.class)
@Uses(Icon.class)
public class ConsultarProductosAbastecerView extends Composite<VerticalLayout> {

    public ConsultarProductosAbastecerView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setHeightFull();
        getContent().setWidthFull();
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("min-content");
        layoutRow.setHeight("min-content");
        buttonPrimary.setText("Buscar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Eliminar");
        buttonSecondary.setWidth("min-content");
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
    }
}
