package com.farmaciafinal.views;

import com.farmaciafinal.views.actualizarestadopedido.ActualizarEstadoPedidoView;
import com.farmaciafinal.views.consultarproductosabastecer.ConsultarProductosAbastecerView;
import com.farmaciafinal.views.estadofinanciero.EstadoFinancieroView;
import com.farmaciafinal.views.facturar.FacturarView;
import com.farmaciafinal.views.facturascliente.FacturasClienteView;
import com.farmaciafinal.views.ingresarcliente.IngresarClienteView;
import com.farmaciafinal.views.ingresarproductos.IngresarProductosView;
import com.farmaciafinal.views.ingresarproveedor.IngresarProveedorView;
import com.farmaciafinal.views.pedidosproducto.PedidosProductoView;
import com.farmaciafinal.views.pedidosproveedor.PedidosProveedorView;
import com.farmaciafinal.views.realizarpedido.RealizarPedidoView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Farmacia");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Ingresar Proveedor", IngresarProveedorView.class, LineAwesomeIcon.USER.create()));
        nav.addItem(new SideNavItem("Realizar Pedido", RealizarPedidoView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Actualizar Estado Pedido", ActualizarEstadoPedidoView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Consultar Productos Abastecer", ConsultarProductosAbastecerView.class,
                LineAwesomeIcon.AMBULANCE_SOLID.create()));
        nav.addItem(new SideNavItem("Ingresar Productos", IngresarProductosView.class,
                LineAwesomeIcon.NOTES_MEDICAL_SOLID.create()));
        nav.addItem(new SideNavItem("Pedidos Producto", PedidosProductoView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Pedidos Proveedor", PedidosProveedorView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Ingresar Cliente", IngresarClienteView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Facturas Cliente", FacturasClienteView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Facturar", FacturarView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));
        nav.addItem(new SideNavItem("Estado Financiero", EstadoFinancieroView.class,
                LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
