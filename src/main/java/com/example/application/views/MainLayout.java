package com.example.application.views;

import com.example.application.views.dashboard.DashboardView;
import com.example.application.views.list.TestCaseView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@PWA(name = "Test artifact management system", shortName = "TAMS")
@CssImport("./themes/myapp/views/shared-styles.css")
@Theme(themeFolder = "myapp", variant = Lumo.DARK)
public class MainLayout extends AppLayout {

    public MainLayout(){
        createHeader();
        createDrawer();
    }

    private void createHeader(){
        H1 logo = new H1("Test artifact management system");
        logo.addClassName("logo");

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer(){
        RouterLink listLink = new RouterLink("List", TestCaseView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(listLink, new RouterLink("Dashboard", DashboardView.class)));
    }
}
