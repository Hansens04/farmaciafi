package com.farmaciafinal.views.actualizarestadopedido;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Actualizar Estado Pedido")
@Route(value = "actualizar-estado-pedido", layout = MainLayout.class)
@Uses(Icon.class)
public class ActualizarEstadoPedidoView extends Composite<VerticalLayout> {

    public ActualizarEstadoPedidoView() {
        TextField textField = new TextField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        ComboBox comboBox = new ComboBox();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary2 = new Button();
        Button buttonSecondary2 = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        textField.setLabel("Buscar Pedido");
        textField.setWidth("min-content");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("min-content");
        layoutRow.setHeight("min-content");
        buttonPrimary.setText("Buscar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Cancelar");
        buttonSecondary.setWidth("min-content");
        comboBox.setLabel("Estado del pedido");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        layoutRow2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("min-content");
        layoutRow2.setHeight("min-content");
        buttonPrimary2.setText("Actualizar");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary2.setText("Cancelar");
        buttonSecondary2.setWidth("min-content");
        getContent().add(textField);
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
        getContent().add(comboBox);
        getContent().add(layoutRow2);
        layoutRow2.add(buttonPrimary2);
        layoutRow2.add(buttonSecondary2);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }
}
