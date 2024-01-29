package com.farmaciafinal.views.estadofinanciero;

import com.farmaciafinal.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.farmaciafinal.models.Contabilidad;
import com.farmaciafinal.models.EncabezadoFactura;
import com.farmaciafinal.models.EncabezadoPedido;

@PageTitle("Estado Financiero")
@Route(value = "estado-financiero", layout = MainLayout.class)
@Uses(Icon.class)
public class EstadoFinancieroView extends Composite<VerticalLayout> {
// Se crea una variable de contabilidad
    private final Contabilidad contabilidad;

    public EstadoFinancieroView() {
        this.contabilidad = new Contabilidad();

        H2 h2 = new H2();
        H4 h4 = new H4();
        H5 h5 = new H5();
        H2 h22 = new H2();
        H4 h42 = new H4();
        H5 h52 = new H5();
        H2 h23 = new H2();
        H5 h53 = new H5();

// se utiliza las variables de mi clase contabilidad
        // Calcular el total de ventas y compras
        double totalVentas = contabilidad.calcularTotalVentas();
        double totalCompras = contabilidad.calcularTotalCompras();

        // Calcular la diferencia entre ventas y compras
        double diferencia = totalVentas - totalCompras;
// se crea los titulos donde se mostrara la informacion
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h2.setText("Ingresos de la farmacia");
        h2.setWidth("max-content");
        h4.setText("Ventas:");
        h4.setWidth("max-content");
        h5.setText("Total de ventas: " + totalVentas);
        h5.setWidth("max-content");
        h22.setText("Egresos de la farmacia");
        h22.setWidth("max-content");
        h42.setText("Total de compras: " + totalCompras);
        h42.setWidth("max-content");
        h23.setText("Patrimonio");
        h23.setWidth("max-content");
        h53.setText("Diferencia (Ventas - Compras): " + diferencia); // Mostrar la diferencia entre ventas y compras
        h53.setWidth("max-content");

        getContent().add(h2);
        getContent().add(h4);
        getContent().add(h5);
        getContent().add(h22);
        getContent().add(h42);
        getContent().add(h52);
        getContent().add(h23);
        getContent().add(h53);
    }}