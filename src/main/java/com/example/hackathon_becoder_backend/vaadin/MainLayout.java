package com.example.hackathon_becoder_backend.vaadin;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@CssImport("styles/shared-styles.css")
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final H1 pageTitle;
    private final RouterLink start;
    private final RouterLink login;
    private final RouterLink signUp;

    private final Button buttonLogOut;
    private final RouterLink homePage;



    public MainLayout(
            @Autowired
            VaadinAuthService vaadinAuthService
    ) {
        // Navigation
        start = new RouterLink("Start Page", StartView.class);
        login = new RouterLink("Login Page", LoginView.class);
        signUp = new RouterLink("Sign Up Page", SignUpView.class);
        homePage = new RouterLink("Home Page", HomeView.class);

        buttonLogOut = new Button("LogOut");
        buttonLogOut.addClickListener(buttonClickEvent -> vaadinAuthService.logOut());
        final UnorderedList list = new UnorderedList(new ListItem(start), new ListItem(login), new ListItem(signUp),new ListItem(homePage));
        final Nav navigation = new Nav(list);
        addToDrawer(navigation);
        setPrimarySection(Section.DRAWER);
        setDrawerOpened(false);

        // Header
        pageTitle = new H1("Fin Tech");
        final Header header = new Header(new DrawerToggle(), pageTitle);
        header.add(buttonLogOut);
        addToNavbar(header);
    }

    private RouterLink[] getRouterLinks() {
        return new RouterLink[]{start, login, signUp};
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        for (final RouterLink routerLink : getRouterLinks()) {
            if (routerLink.getHighlightCondition().shouldHighlight(routerLink, event)) {
                pageTitle.setText(routerLink.getText());
            }
        }
    }
}